package com.user_services.user_services.events;

import java.time.LocalDateTime;

public record AddressCreatedEvent(
        Long addressId,
        Long personId,
        String street,
        String streetNumber,
        String apartmentNumber,
        String neighborhood,
        String city,
        String state,
        String postalCode,
        String countryCode,
        LocalDateTime createdAt
) {}