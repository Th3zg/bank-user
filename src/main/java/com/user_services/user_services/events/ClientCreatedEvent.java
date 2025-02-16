package com.user_services.user_services.events;

import com.user_services.user_services.enums.RiskLevel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClientCreatedEvent(
        Long clientId,
        Long personId,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String gender,
        String profileImageUrl,
        String communicationPreference,
        Boolean termsAccepted,
        String accountNumber,
        BigDecimal accountBalance,
        BigDecimal overdraftLimit,
        RiskLevel riskLevel,
        BigDecimal creditScore,
        BigDecimal totalLoans,
        BigDecimal totalInvestments,
        BigDecimal totalInsurance,
        BigDecimal monthlyIncome,
        String occupation,
        String maritalStatus,
        LocalDateTime createdAt
) {
  public ClientCreatedEvent(Long clientId, Long personId, String firstName, String lastName, String email, LocalDate dateOfBirth, String accountNumber, BigDecimal accountBalance) {
    this(
            clientId,
            personId,
            firstName,
            lastName,
            email,
            dateOfBirth,
            "Unknown",
            null,
            "Email",
            false,
            accountNumber,
            accountBalance,
            BigDecimal.ZERO,
            RiskLevel.LOW,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            "Unknown",
            "Unknown",
            LocalDateTime.now()
    );
  }
}