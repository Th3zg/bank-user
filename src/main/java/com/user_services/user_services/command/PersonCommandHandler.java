package com.user_services.user_services.command;

import com.user_services.user_services.model.Person;
import com.user_services.user_services.repositories.PersonRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class PersonCommandHandler {
  private final PersonRepositoryImpl personRepository;
  private final TransactionTemplate transactionTemplate;
  
  public Result<Void> handle(CreatePersonCommand command) {
    return transactionTemplate.execute(status -> {
      Person person = new Person.Builder()
              .setFirstName(command.firstName())
              .setLastName((command.lastName()))
              .setDateBirth(command.dateBirth())
              .setEmail(command.email())
              .setPassword(command.password())
              .setGender(command.gender())
              .setProfileImageUrl(command.profileImageUrl())
              .setCommunicationPreference(command.communicationPreference())
              .build();

      Try<Long> resultPersonCreation = personRepository.create(person);
      if (resultPersonCreation.isFailure()) {
        status.setRollbackOnly();
        return Result.failure(Collections.singleton("Error: " + resultPersonCreation.getCause().getMessage()));
      }

      Long id = resultPersonCreation.get();
      return Result.success();
    });
  }
}
