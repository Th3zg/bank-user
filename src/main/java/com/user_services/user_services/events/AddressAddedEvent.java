package com.user_services.user_services.events;

import java.time.LocalDateTime;

public record AddressAddedEvent(
        Long addressId,
        Long personId,
        String street,
        String streetNumber,
        String neighborhood,
        String city,
        String state,
        String postalCode,
        String countryCode,
        LocalDateTime createdAt
) {
  public AddressAddedEvent(Long addressId, Long personId, String street, String streetNumber,
                           String neighborhood, String city, String state, String postalCode, String countryCode) {
    this(addressId, personId, street, streetNumber, neighborhood, city, state, postalCode, countryCode, LocalDateTime.now());
  }
}