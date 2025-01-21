package com.user_services.user_services.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_services.user_services.enums.RiskLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ClientRequest(
        @NotNull(message = "The account balance cannot be zero")
        @JsonProperty("account_balance") BigDecimal accountBalance,

        @NotNull(message = "The overdraft limit cannot be zero")
        @PositiveOrZero
        @JsonProperty("overdraft_limit") BigDecimal overdraftLimit,

        @NotNull(message = "The risk level cannot be zero")
        @Pattern(regexp = "low|medium|high", message = "The risk level must be 'low', 'medium' or 'high'")
        @JsonProperty("risk_level") RiskLevel riskLevel,

        @Max(value = 850, message = "Credit score cannot exceed 850")
        @Min(value = 300, message = "Credit score must be at least 300")
        @JsonProperty("credit_score") BigDecimal creditScore,

        @Positive(message = "Total loans must be positive")
        @JsonProperty("total_loans") BigDecimal totalLoans,

        @PositiveOrZero(message = "Total investments must be zero or positive")
        @JsonProperty("total_investments") BigDecimal totalInvestments,

        @Positive(message = "The total insurance must be positive")
        @JsonProperty("total_insurance") BigDecimal totalInsurance
) {
}
