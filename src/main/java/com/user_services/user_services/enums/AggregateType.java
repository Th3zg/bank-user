package com.user_services.user_services.enums;

public enum AggregateType {
  PERSON("person"),
  CLIENT("client"),
  PHONE("phone"),
  ADDRESS("address");

  private final String value;

  AggregateType(String value) { this.value = value; }

  public String getValue() { return value; }
}
