package com.user_services.user_services.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Field {
  ROLE(Set.of("ADMIN", "USER", "GUEST")),
  GENDER(Set.of("MALE", "FEMALE", "OTHER")),
  PHONE_TYPE(Set.of("MOBILE", "HOME", "WORK"));

  private Set<String> values;

  Field(Set<String> value) {}
}
