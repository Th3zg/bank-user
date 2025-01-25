package com.user_services.user_services.services;

import com.user_services.user_services.command.CreateUserCommand;
import com.user_services.user_services.domain.Person;
import com.user_services.user_services.repositories.PersonRepositoryImpl;
import com.user_services.user_services.services.interfaces.UserServices;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

@Service
public class UserServicesImpl implements UserServices {
  private final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);

  private final TransactionTemplate transactionTemplate;
  private final PersonRepositoryImpl personRepository;

  @Autowired
  public UserServicesImpl(TransactionTemplate transactionTemplate,
                              PersonRepositoryImpl personRepository) {
    this.transactionTemplate = transactionTemplate;
    this.personRepository = personRepository;
  }

  @Override
  public Result<?> getAllUsers() {
    return null;
  }

  @Override
  public Result<?> getUserById() {
    return null;
  }

  @Override
  public Result<Void> createUser(CreateUserCommand request) {
    return transactionTemplate.execute(status -> {
      Person person = new Person.Builder()
              .setFirstName(request.firstName())
              .setLastName((request.lastName()))
              .setDateBirth(request.dateBirth())
              .setEmail(request.email())
              .setPassword(request.password())
              .setGender(request.gender())
              .setProfileImageUrl(request.profileImageUrl())
              .setCommunicationPreference(request.communicationPreference())
              .build();

      Try<Long> creationPersonResult = personRepository.create(person);
      if (creationPersonResult.isFailure()) {
        status.setRollbackOnly();
        return Result.failure(Collections.singleton("Error: " + creationPersonResult.getCause().getMessage()));
      }

      Long id = creationPersonResult.get();
      return Result.success();
    });
  }

  @Override
  public Result<?> updateUser() {
    return null;
  }

  @Override
  public Result<?> deleteUser() {
    return null;
  }

  @Override
  public Result<?> searchUsers() {
    return null;
  }
}