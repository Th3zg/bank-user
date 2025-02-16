package com.user_services.user_services.repositories;

import com.user_services.user_services.exception.DatabaseErrorExceptionMapper;
import com.user_services.user_services.model.entity.Client;
import com.user_services.user_services.repositories.interfaces.UserRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements UserRepository {
  private final Logger logger = LoggerFactory.getLogger(ClientRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Try<Void> create(Client client) {
    String sql = "INSERT INTO clients (person_id, account_number, account_balance, " +
            "overdraft_limit, risk_level, credit_score, total_loans, total_investments, " +
            "total_insurance, monthly_income, occupation, marital_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    return Try.run(() -> {
      jdbcTemplate.update(sql,
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
              client.getMaritalStatus()
      );
      logger.info("Client created successfully");
    }).onFailure(ex -> {
      logger.error("Error creating client", ex);
      DatabaseErrorExceptionMapper.fromException((DataAccessException) ex);
    });
  }
}
