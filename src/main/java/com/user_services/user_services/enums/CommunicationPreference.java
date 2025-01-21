package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum CommunicationPreference {
  EMAIL("email"),
  SMS("sms"),
  PHONE("phone"),
  PUSH_NOTIFICATION("push_notification");

  private final String value;
  CommunicationPreference(String value) { this.value = value; }

}
