package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreatePersonCommand;
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
import com.user_services.user_services.repositories.PersonRepositoryImpl;
import com.user_services.user_services.repositories.interfaces.OutboxRepository;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
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
  private final OutboxRepository outboxRepository;
  private final ApplicationEventPublisher eventPublisher;

  public Result<Void> handler(CreatePersonCommand command) {
    return transactionTemplate.execute(status -> {
      // create the person
      Person person = createPerson(command);

      Try<Long> resultPersonCreation = personRepository.create(person);
      if (resultPersonCreation.isFailure()) {
        status.setRollbackOnly();
        return Result.failure("Error: " + resultPersonCreation.getCause().getMessage());
      }

      // get id of the peron
      Long personId = resultPersonCreation.get();

      // Pass the id to the client, the address and the phone to create them
      Result<Client> clientResult = clientCommandHandler.handler(personId, command.client());
      Result<Address> addressResult = addressCommandHandler.handler(personId, command.address());
      Result<Phone> phoneResult = phoneCommandHandler.handler(personId, command.phone());

      if (clientResult.isFailure() || addressResult.isFailure() || phoneResult.isFailure()) {
        logger.error("Error: {} {} {}", clientResult.errors(), addressResult.errors(), phoneResult.errors());
        return Result.failure("failed to create client");
      }

      Client client = clientResult.getValue();
      Address address = addressResult.getValue();
      Phone phone = phoneResult.getValue();

      outboxRepository.insert(new OutboxEvent(
              null,
              "Person",
              person.getId(),
              "PersonCreatedEvent",
              new PersonCreatedEvent(
                      person.getId(),
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
              ),
              OutboxStatus.PENDING
      ));

      outboxRepository.insert(new OutboxEvent(
              null,
              "Client",
              client.getId(),
              "ClientCreatedEvent",
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
                      LocalDateTime.MIN
              ),
              OutboxStatus.PENDING
      ));

      outboxRepository.insert(new OutboxEvent(
              null,
              "Address",
              address.getAddressId(),
              "AddressCreatedEvent",
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
              ),
              OutboxStatus.PENDING
      ));

      outboxRepository.insert(new OutboxEvent(
              null,
              "Phone",
              phone.getPhoneId(),
              "PhoneCreatedEvent",
              new PhoneCreatedEvent(
                      phone.getPhoneId(),
                      phone.getPersonId(),
                      phone.getPhoneNumber(),
                      phone.getPhoneType(),
                      LocalDateTime.now()
              ),
              OutboxStatus.PENDING
      ));

      return Result.success();
    });
  }

  private Person createPerson(CreatePersonCommand command) {
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

  private void createOutboxEvent(String aggregate_type,
                                 long aggregate_id,
                                 String type,
                                 Object payload,
                                 OutboxStatus status,
                                 Object event) {
    outboxRepository.insert(new OutboxEvent(
            null,
            aggregate_type,
            aggregate_id,
            type,
            payload,
            status
    ));
  }
}
