package com.user_services.user_services.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonCreatedEvent(
        Long personId,
        String firstName,
        String lastName,
        String email,
        LocalDate dateBirth,
        LocalDateTime createdAt
) {
  public PersonCreatedEvent(Long personId, String firstName, String lastName, String email, LocalDate dateBirth) {
    this(personId, firstName, lastName, email, dateBirth, LocalDateTime.now());
  }
}