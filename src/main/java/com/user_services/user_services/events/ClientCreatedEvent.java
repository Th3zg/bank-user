package com.user_services.user_services.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ClientCreatedEvent(
        Long clientId,
        Long personId,
        String accountNumber,
        BigDecimal accountBalance,
        LocalDateTime createdAt
) {
  public ClientCreatedEvent(Long clientId, Long personId, String accountNumber, BigDecimal accountBalance) {
    this(clientId, personId, accountNumber, accountBalance, LocalDateTime.now());
  }
}