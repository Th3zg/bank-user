package com.user_services.user_services.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_services.user_services.enums.RiskLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateClientCommand(
        @NotNull(message = "Person ID is required")
        @JsonProperty("person_id") Long personId,

        @NotBlank(message = "Account number is required")
        @Size(max = 20, message = "Account number cannot exceed 20 characters")
        @JsonProperty("account_number") String accountNumber,

        @NotNull(message = "Account balance cannot be null")
        @DecimalMin(value = "0.00", message = "Account balance must be non-negative")
        @JsonProperty("account_balance") BigDecimal accountBalance,

        @NotNull(message = "Overdraft limit cannot be null")
        @DecimalMin(value = "0.00", message = "Overdraft limit must be non-negative")
        @JsonProperty("overdraft_limit") BigDecimal overdraftLimit,

        @NotNull(message = "Risk level is required")
        @JsonProperty("risk_level") RiskLevel riskLevel,

        @DecimalMin(value = "300.00", message = "Credit score must be at least 300")
        @DecimalMax(value = "850.00", message = "Credit score cannot exceed 850")
        @JsonProperty("credit_score") BigDecimal creditScore,

        @DecimalMin(value = "0.00", message = "Total loans must be non-negative")
        @JsonProperty("total_loans") BigDecimal totalLoans,

        @DecimalMin(value = "0.00", message = "Total investments must be non-negative")
        @JsonProperty("total_investments") BigDecimal totalInvestments,

        @DecimalMin(value = "0.00", message = "Total insurance must be non-negative")
        @JsonProperty("total_insurance") BigDecimal totalInsurance,

        @NotNull(message = "Monthly income is required")
        @DecimalMin(value = "0.00", message = "Monthly income must be non-negative")
        @JsonProperty("monthly_income") BigDecimal monthlyIncome,

        @JsonProperty("occupation") String occupation,

        @JsonProperty("marital_status") String maritalStatus
) {
}
