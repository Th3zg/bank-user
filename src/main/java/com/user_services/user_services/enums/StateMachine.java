package com.user_services.user_services.enums;

public enum StateMachine {
  PENDING_VERIFICATION("pending_verification"),
  ACTIVE("active"),
  BLOCKED("blocked"),
  SUSPENDED("suspended"),
  CLOSED("closed"),
  REJECTED("rejected");

  private final String value;

  StateMachine(String value) {
    this.value = value;
  }
}
