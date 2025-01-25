package com.user_services.user_services.domain;

import com.user_services.user_services.enums.RiskLevel;

import java.math.BigDecimal;

public final class Client {
  private final Long id;
  private final Long personId;
  private final String accountNumber;
  private final BigDecimal accountBalance;
  private final BigDecimal overdraftLimit;
  private final com.user_services.user_services.enums.RiskLevel riskLevel;
  private final BigDecimal creditScore;
  private final BigDecimal totalLoans;
  private final BigDecimal totalInvestments;
  private final BigDecimal totalInsurance;

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

    public Client build() {
      return new Client(this);
    }
  }

  public Long getId() { return id; }
  public Long getPersonId() { return personId; }
  public String getAccountNumber() { return accountNumber; }
  public BigDecimal getAccountBalance() { return accountBalance; }
  public BigDecimal getOverdraftLimit() { return overdraftLimit; }
  public RiskLevel getRiskLevel() { return riskLevel; }
  public BigDecimal getCreditScore() { return creditScore; }
  public BigDecimal getTotalLoans() { return totalLoans; }
  public BigDecimal getTotalInvestments() { return totalInvestments; }
  public BigDecimal getTotalInsurance() { return totalInsurance; }
}