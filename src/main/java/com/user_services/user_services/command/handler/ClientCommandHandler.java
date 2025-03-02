package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreateClientCommand;
import com.user_services.user_services.model.entity.Client;
import com.user_services.user_services.repositories.ClientRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(ClientCommandHandler.class);
  private final ClientRepositoryImpl clientRepository;

  public Result<Void> handler(long personsId, CreateClientCommand command) {
    // create the client
    Client client = createClient(personsId, command);

    Try<Void> resultClientCreation = clientRepository.create(client);
    if (resultClientCreation.isFailure()) {
      return Result.failure("Error: " + resultClientCreation.getCause().getMessage());
    }
    return Result.success();
  }

  private Client createClient(long personsId, CreateClientCommand command) {
    return new Client.Builder()
            .setPersonId(personsId)
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
  }
}

