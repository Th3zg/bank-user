package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreatePersonCommand;
import com.user_services.user_services.enums.AggregateType;
import com.user_services.user_services.enums.EventType;
import com.user_services.user_services.enums.OutboxStatus;
import com.user_services.user_services.events.AddressCreatedEvent;
import com.user_services.user_services.events.ClientCreatedEvent;
import com.user_services.user_services.events.PersonCreatedEvent;
import com.user_services.user_services.events.PhoneCreatedEvent;
import com.user_services.user_services.model.entity.Address;
import com.user_services.user_services.model.entity.Client;
import com.user_services.user_services.model.entity.Person;
import com.user_services.user_services.model.entity.Phone;
import com.user_services.user_services.outbox.OutboxEvent;
import com.user_services.user_services.repositories.OutboxRepositoryImpl;
import com.user_services.user_services.repositories.PersonRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PersonCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(PersonCommandHandler.class);
  private final PersonRepositoryImpl personRepository;
  private final TransactionTemplate transactionTemplate;
  private final ClientCommandHandler clientCommandHandler;
  private final AddressCommandHandler addressCommandHandler;
  private final PhoneCommandHandler phoneCommandHandler;
  private final OutboxRepositoryImpl outboxRepository;
  private final ApplicationEventPublisher eventPublisher;

  public Result<Void> handler(CreatePersonCommand command) {
    return transactionTemplate.execute(status -> {
      return Try.of(() -> createPersonEntity(command))
              .flatMap(person -> persistPerson(person, status))
              .flatMap(person -> createRelatedEntities(person, command, status))
              .fold(err -> {
                status.setRollbackOnly();
                return Result.failure("Error: " + err.getMessage());
                      },
                      success -> Result.success());
    });
  }

  private Person createPersonEntity(CreatePersonCommand command) {
    return new Person.Builder()
            .setFirstName(command.firstName())
            .setLastName((command.lastName()))
            .setDateBirth(command.dateBirth())
            .setEmail(command.email())
            .setPassword(command.password())
            .setGender(command.gender())
            .setProfileImageUrl(command.profileImageUrl())
            .setCommunicationPreference(command.communicationPreference())
            .build();
  }

  private Try<Person> persistPerson(Person person, TransactionStatus status) {
    return personRepository.create(person)
            .onFailure(err -> {
              logger.error("Error creating person: {}", err.getMessage());
              status.setRollbackOnly();
            });
  }

  private Try<Try<Void>> createRelatedEntities(Person person, CreatePersonCommand command, TransactionStatus status) {
    return Try.of(() -> {
      Try<Client> clientTry = clientCommandHandler.handler(person.getId(), command.client());
      Try<Address> addressTry = addressCommandHandler.handler(person.getId(), command.address());
      Try<Phone> phoneTry = phoneCommandHandler.handler(person.getId(), command.phone());

      return Try.run(() -> {
                Client client = clientTry.getOrElse(() -> {
                  logger.error("Error creating client");
                  return null;
                });
                Address address = addressTry.getOrElse(() -> {
                  logger.error("Error creating address");
                  return null;
                });
                Phone phone = phoneTry.getOrElse(() -> {
                  logger.error("Error creating phone");
                  return null;
                });
                generateOutboxEvents(person, client, address, phone);
              })
              .onFailure(ex -> logger.error("Error creating related entities", ex));
    });
  }

  private void generateOutboxEvents(Person person, Client client, Address address, Phone phone) {
    createPersonOutboxEvent(person.getId(), person);
    createClientOutboxEvent(client.getId(), client);
    createAddressOutboxEvent(address.getAddressId(), address);
    createPhoneOutboxEvent(phone.getPhoneId(), phone);
  }

  private void createOutboxEvent(AggregateType aggregateType, long aggregateId, EventType eventType, Object payload) {
    Try.of(() -> outboxRepository.insert(buildOutboxEvent(aggregateType, aggregateId, eventType, payload)))
            .onFailure(err -> {
              logger.error("Error inserting {} event for ID {}: {}", eventType, aggregateId, err.getMessage());
            });
  }

  private OutboxEvent buildOutboxEvent(AggregateType aggregateType, Long aggregateId, EventType eventType, Object payload) {
    return new OutboxEvent(
            null,
            aggregateType,
            aggregateId,
            eventType,
            payload,
            OutboxStatus.PENDING,
            null,
            0,
            null,
            null,
            null
    );
  }

  // OutboxEvents
  private void createPersonOutboxEvent(long personId, Person person) {
    createOutboxEvent(
            AggregateType.PERSON,
            personId,
            EventType.PERSON_CREATED,
            new PersonCreatedEvent(
                    personId,
                    person.getFirstName(),
                    person.getLastName(),
                    person.getEmail(),
                    person.getDateBirth(),
                    person.getGender().getValue(),
                    person.getProfileImageUrl(),
                    person.getCommunicationPreference().getValue(),
                    person.isTermsAccepted(),
                    person.getBio(),
                    LocalDateTime.now()
            )
    );
  }

  private void createClientOutboxEvent(long clientId, Client client) {
    createOutboxEvent(
            AggregateType.CLIENT,
            clientId,
            EventType.CLIENT_REGISTERED,
            new ClientCreatedEvent(
                    client.getId(),
                    client.getPersonId(),
                    client.getAccountNumber(),
                    client.getAccountBalance(),
                    client.getOverdraftLimit(),
                    client.getRiskLevel(),
                    client.getCreditScore(),
                    client.getTotalLoans(),
                    client.getTotalInvestments(),
                    client.getTotalInsurance(),
                    client.getMonthlyIncome(),
                    client.getOccupation(),
                    client.getMaritalStatus(),
                    LocalDateTime.now()
            )
    );
  }

  private void createAddressOutboxEvent(long addressId, Address address) {
    createOutboxEvent(
            AggregateType.ADDRESS,
            addressId,
            EventType.ADDRESS_CREATED,
            new AddressCreatedEvent(
                    address.getAddressId(),
                    address.getPersonId(),
                    address.getStreet(),
                    address.getStreetNumber(),
                    address.getApartmentNumber(),
                    address.getNeighborhood(),
                    address.getCity(),
                    address.getState(),
                    address.getPostalCode(),
                    address.getCountryCode(),
                    LocalDateTime.now()
            )
    );
  }

  private void createPhoneOutboxEvent(long phoneId, Phone phone) {
    createOutboxEvent(
            AggregateType.PHONE,
            phoneId,
            EventType.PHONE_ADDED,
            new PhoneCreatedEvent(
                    phone.getPhoneId(),
                    phone.getPersonId(),
                    phone.getPhoneNumber(),
                    phone.getPhoneType(),
                    LocalDateTime.now()
            )
    );
  }
}
