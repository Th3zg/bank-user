package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum Gender {
  MALE("male"),
  FEMALE("female"),
  OTHER("other");

  private final String value;

  Gender(String value) {
    this.value = value;
  }

}
