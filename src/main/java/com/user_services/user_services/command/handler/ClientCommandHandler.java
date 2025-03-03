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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class ClientCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(ClientCommandHandler.class);
  private final ClientRepositoryImpl clientRepository;
  private final TransactionTemplate transactionTemplate;

  public Try<Client> handler(long personsId, CreateClientCommand command) {
    return transactionTemplate.execute(status -> {
      // create client
      return Try.of(() -> createClient(personsId, command))
              .flatMap(client -> persistClient(client, status))
              .map(client -> {
                logger.info("Client created successfully: {}", client.getId());
                return client;
              });
    });
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

  private Try<Client> persistClient(Client client, TransactionStatus status) {
    return clientRepository.create(client)
            .onFailure(err -> {
              logger.error("Error creating client: {}", err.getMessage());
              status.setRollbackOnly();
            });
  }
}

