package com.user_services.user_services.command;

import com.user_services.user_services.events.PersonCreatedEvent;
import com.user_services.user_services.model.Person;
import com.user_services.user_services.repositories.PersonRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class PersonCommandHandler {
  private final PersonRepositoryImpl personRepository;
  private final TransactionTemplate transactionTemplate;
  private final ApplicationEventPublisher eventPublisher;
  
  public Result<Void> handle(CreatePersonCommand command) {
    return transactionTemplate.execute(status -> {
      // create the person
      Person person = createPerson(command);

      Try<Long> resultPersonCreation = personRepository.create(person);
      if (resultPersonCreation.isFailure()) {
        status.setRollbackOnly();
        return Result.failure(Collections.singleton("Error: " + resultPersonCreation.getCause().getMessage()));
      }

      //get id of the peron
      Long id = resultPersonCreation.get();

      eventPublisher.publishEvent(new PersonCreatedEvent(id,
              person.getFirstName(),
              person.getLastName(),
              person.getEmail(),
              person.getDateBirth()));

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
}
