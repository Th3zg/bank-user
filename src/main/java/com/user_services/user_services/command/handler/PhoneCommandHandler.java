package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreatePhoneCommand;
import com.user_services.user_services.model.entity.Phone;
import com.user_services.user_services.repositories.PhoneRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class PhoneCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(PhoneCommandHandler.class);
  private final PhoneRepositoryImpl phoneRepository;
  private final TransactionTemplate transactionTemplate;

  public Result<Phone> handler(Long personId, CreatePhoneCommand command) {
    // create the phone
    Phone phone = createPhone(personId, command);

    Try<Void> resultPhoneCreation = phoneRepository.create(phone);
    if (resultPhoneCreation.isFailure()) {
      return Result.failure("Error: " + resultPhoneCreation.getCause().getMessage());
    }
    return Result.success(phone);
  }
  private Phone createPhone(long personsId, CreatePhoneCommand command) {
    return new Phone.Builder()
            .setPhoneId(personsId)
            .setPhoneNumber(command.phoneNumber())
            .setPhoneType(command.phoneType())
            .build();
  }
}

