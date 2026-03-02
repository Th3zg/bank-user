package com.user_services.user_services.model.entity;

import com.user_services.user_services.enums.RiskLevel;
import com.user_services.user_services.enums.StateMachine;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class Client {
  private final Long id;
  private final Long personId;
  private final String accountNumber;
  private final BigDecimal accountBalance;
  private final BigDecimal overdraftLimit;
  private final RiskLevel riskLevel;
  private final BigDecimal creditScore;
  private final BigDecimal totalLoans;
  private final BigDecimal totalInvestments;
  private final BigDecimal totalInsurance;
  private final BigDecimal monthlyIncome;
  private final String occupation;
  private final String maritalStatus;
  private final StateMachine stateMachine;

  public Client(Builder builder) {
    this.id = builder.id;
    this.personId = builder.personId;
    this.accountNumber = builder.accountNumber;
    this.accountBalance = builder.accountBalance;
    this.overdraftLimit = builder.overdraftLimit;
    this.riskLevel = builder.riskLevel;
    this.creditScore = builder.creditScore;
    this.totalLoans = builder.totalLoans;
    this.totalInvestments = builder.totalInvestments;
    this.totalInsurance = builder.totalInsurance;
    this.monthlyIncome = builder.monthlyIncome;
    this.occupation = builder.occupation;
    this.maritalStatus = builder.maritalStatus;
    this.stateMachine = builder.stateMachine;
  }

  public static class Builder {
    private Long id;
    private Long personId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private BigDecimal overdraftLimit;
    private RiskLevel riskLevel;
    private BigDecimal creditScore;
    private BigDecimal totalLoans;
    private BigDecimal totalInvestments;
    private BigDecimal totalInsurance;
    private BigDecimal monthlyIncome;
    private String occupation;
    private String maritalStatus;
    private StateMachine stateMachine;

    public Builder setId(Long id) { this.id = id; return this; }
    public Builder setPersonId(Long personId) { this.personId = personId; return this; }
    public Builder setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; return this; }
    public Builder setAccountBalance(BigDecimal accountBalance) { this.accountBalance = accountBalance; return this; }
    public Builder setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; return this; }
    public Builder setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; return this; }
    public Builder setCreditScore(BigDecimal creditScore) { this.creditScore = creditScore; return this; }
    public Builder setTotalLoans(BigDecimal totalLoans) { this.totalLoans = totalLoans; return this; }
    public Builder setTotalInvestments(BigDecimal totalInvestments) { this.totalInvestments = totalInvestments; return this; }
    public Builder setTotalInsurance(BigDecimal totalInsurance) { this.totalInsurance = totalInsurance; return this; }
    public Builder setMonthlyIncome(BigDecimal monthlyIncome) { this.monthlyIncome = monthlyIncome; return this; }
    public Builder setOccupation(String occupation) { this.occupation = occupation; return this; }
    public Builder setMaritalStatus(String maritalStatus) { this.maritalStatus  = maritalStatus; return this; }
    public Builder setStateMachine(StateMachine stateMachine) { this.stateMachine = stateMachine; return this; }

    public Client build() {
      return new Client(this);
    }
  }
}