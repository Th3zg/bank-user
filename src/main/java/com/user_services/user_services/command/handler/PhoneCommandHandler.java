package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreatePhoneCommand;
import com.user_services.user_services.model.entity.Phone;
import com.user_services.user_services.repositories.PhoneRepositoryImpl;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class PhoneCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(PhoneCommandHandler.class);
  private final PhoneRepositoryImpl phoneRepository;
  private final TransactionTemplate transactionTemplate;

  public Try<Phone> handler(Long personId, CreatePhoneCommand command) {
    return transactionTemplate.execute(status -> {
      return Try.of(() -> createPhone(personId, command))
              .flatMap(phone -> persistPhone(phone, status))
              .map(phone -> {
                logger.info("Client created successfully: {}", phone.getPhoneId());
                return phone;
              });
    });

  }
  private Phone createPhone(long personsId, CreatePhoneCommand command) {
    return new Phone.Builder()
            .setPhoneId(personsId)
            .setPhoneNumber(command.phoneNumber())
            .setPhoneType(command.phoneType())
            .build();
  }

  private Try<Phone> persistPhone(Phone phone, TransactionStatus status) {
    return phoneRepository.create(phone)
            .onFailure(err -> {
              logger.error("Error creating phone: {}", err.getMessage());
              status.setRollbackOnly();
            });
  }
}

