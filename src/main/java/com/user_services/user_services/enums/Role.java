package com.user_services.user_services.enums;

public enum Role {
  ADMIN("admin"),
  USER("user"),
  GUEST("guest");

  private final String value;

  Role(String value) { this.value = value; }
}
