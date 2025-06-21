package com.user_services.user_services.model.entity;

import com.user_services.user_services.enums.PhoneType;
import lombok.Getter;
import java.time.ZonedDateTime;

@Getter
public final class Phone {
  private final Long phoneId;
  private final Long personId;
  private final String phoneNumber;
  private final PhoneType phoneType;
  private final ZonedDateTime createdAt;
  private final ZonedDateTime updatedAt;

  private Phone(Builder builder) {
    this.phoneId = builder.phoneId;
    this.personId = builder.personId;
    this.phoneNumber = builder.phoneNumber;
    this.phoneType = builder.phoneType;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  public static class Builder {
    private Long phoneId;
    private Long personId;
    private String phoneNumber;
    private PhoneType phoneType;
    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public Builder setPhoneId(Long phoneId) { this.phoneId = phoneId; return this; }
    public Builder setPersonId(Long personId) { this.personId = personId; return this; }
    public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
    public Builder setPhoneType(PhoneType phoneType) { this.phoneType = phoneType; return this; }
    public Builder setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; return this; }
    public Builder setUpdatedAt(ZonedDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

    public Phone build() {
      return new Phone(this);
    }
  }
}