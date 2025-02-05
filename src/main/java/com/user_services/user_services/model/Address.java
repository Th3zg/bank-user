package com.user_services.user_services.model;

import com.user_services.user_services.util.Result;
import com.user_services.user_services.util.ValidationUtils;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
public final class Address {
  private final Long addressId;
  private final Long personId;
  private final String street;
  private final String streetNumber;
  private final String apartmentNumber;
  private final String neighborhood;
  private final String city;
  private final String state;
  private final String postalCode;
  private final ZonedDateTime createdAt;
  private final ZonedDateTime updatedAt;
  private final String countryCode;

  private Address(Builder builder) {
    this.addressId = builder.addressId;
    this.personId = builder.personId;
    this.street = builder.street;
    this.streetNumber = builder.streetNumber;
    this.apartmentNumber = builder.apartmentNumber;
    this.neighborhood = builder.neighborhood;
    this.city = builder.city;
    this.state = builder.state;
    this.postalCode = builder.postalCode;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.countryCode = builder.countryCode;
  }

  public static class Builder {
    private Long addressId;
    private Long personId;
    private String street;
    private String streetNumber;
    private String apartmentNumber;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();
    private String countryCode;

    public Builder setAddressId(Long addressId) { this.addressId = addressId; return this; }
    public Builder setPersonId(Long personId) { this.personId = personId; return this; }
    public Builder setStreet(String street) { this.street = street; return this; }
    public Builder setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; return this; }
    public Builder setApartmentNumber(String apartmentNumber) { this.apartmentNumber = apartmentNumber; return this; }
    public Builder setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; return this; }
    public Builder setCity(String city) { this.city = city; return this; }
    public Builder setState(String state) { this.state = state; return this; }
    public Builder setPostalCode(String postalCode) { this.postalCode = postalCode; return this; }
    public Builder setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; return this; }
    public Builder setUpdatedAt(ZonedDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
    public Builder setCountryCode(String countryCode) { this.countryCode = countryCode; return this; }

    public Result<Address> build() {
      Map<String, Object> fields = Map.of(
              "personId", personId,
              "street", street,
              "streetNumber", streetNumber,
              "neighborhood", neighborhood,
              "city", city,
              "state", state,
              "postalCode", postalCode,
              "countryCode", countryCode
      );
      return ValidationUtils.validateFields(fields, () -> new Address(this));
    }
  }
}