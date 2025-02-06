package com.user_services.user_services.command;

import com.user_services.user_services.model.Client;
import com.user_services.user_services.repositories.ClientRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ClientCommandHandler {
  private final ClientRepositoryImpl clientRepository;
  private final TransactionTemplate transactionTemplate;

  public Result<Void> handle(long id, CreateClientCommand command) {
    return transactionTemplate.execute(status -> {
      Client client = new Client.Builder()
              .setId(id)
              .setPersonId(command.personId())
              .setAccountNumber(command.accountNumber())
              .setAccountBalance(command.accountBalance())
              .setOverdraftLimit(command.overdraftLimit())
              .setRiskLevel(command.riskLevel())
              .setCreditScore(command.creditScore())
              .setTotalLoans(command.totalLoans())
              .setTotalInsurance(command.totalInsurance())
              .setTotalInvestments(command.totalInvestments())
              .setMonthlyIncome(command.monthlyIncome())
              .setOccupation(command.occupation())
              .setMaritalStatus(command.maritalStatus())
              .build();

      Try<Void> resultClientCreation = clientRepository.create(client);
      if (resultClientCreation.isFailure()) {
        status.setRollbackOnly();
        return Result.failure(Collections.singleton("Error: " + resultClientCreation.getCause().getMessage()));
      }
      return Result.success();
    });
  }
}

