package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum PhoneType {
  MOBILE("mobile"),
  FIXED("fixed"),
  WORK("work"),
  EMERGENCY("emergency");

  private final String value;

  PhoneType(String value) { this.value = value; }

}
