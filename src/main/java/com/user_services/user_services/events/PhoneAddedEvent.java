package com.user_services.user_services.events;

import com.user_services.user_services.enums.PhoneType;

import java.time.LocalDateTime;

public record PhoneAddedEvent(
        Long phoneId,
        Long personId,
        String phoneNumber,
        PhoneType phoneType,
        LocalDateTime createdAt
) {}