package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum EventType {
  PERSON_CREATED_EVENT("personCreatedEvent"),
  CLIENT_CREATED_EVENT("clientCreatedEvent"),
  PHONE_CREATED_EVENT("phoneCreatedEvent"),
  ADDRESS_CREATED_EVENT("addressCreatedEvent"),
  PERSON_CREATED("person_created"),
  PERSON_UPDATED("person_updated"),
  PERSON_DEACTIVATED("person_deactivated"),
  CLIENT_REGISTERED("client_registered"),
  CLIENT_UPGRADED("client_tier_upgraded"),
  CLIENT_DELETED("client_deleted"),
  PHONE_ADDED("phone_added"),
  PHONE_VERIFIED("phone_verified"),
  PHONE_PRIMARY_CHANGED("phone_primary_changed"),
  ADDRESS_CREATED("address_created"),
  ADDRESS_UPDATED("address_updated"),
  ADDRESS_DELETED("address_deleted");

  private final String value;

  EventType(String value) { this.value = value; }
}
