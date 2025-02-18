package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum OutboxStatus {
  PENDING("pending"),
  PROCESSED("processed"),
  FAILED("failed");

  private final String value;

  OutboxStatus(String value) { this.value = value; }
}
