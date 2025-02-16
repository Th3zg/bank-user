package com.user_services.user_services.events;

import com.user_services.user_services.enums.RiskLevel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ClientCreatedEvent(
        Long clientId,
        Long personId,
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
) {}