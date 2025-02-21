package com.user_services.user_services.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonCreatedEvent(
        Long personId,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String gender,
        String profileImageUrl,
        String communicationPreference,
        Boolean termsAccepted,
        String bio,
        LocalDateTime createdAt
) {}