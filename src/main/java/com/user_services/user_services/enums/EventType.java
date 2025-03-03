package com.user_services.user_services.enums;

public enum EventType {
  PERSON_CREATED_EVENT("personCreatedEvent"),
  CLIENT_CREATED_EVENT("clientCreatedEvent"),
  PHONE_CREATED_EVENT("phoneCreatedEvent"),
  ADDRESS_CREATED_EVENT("addressCreatedEvent");

  private final String value;

  EventType(String value) { this.value = value; }

  public String getValue() {
    return value;
  }
}
