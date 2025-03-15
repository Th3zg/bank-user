package com.user_services.user_services.enums;

import java.util.Set;

public enum Field {
  ROLE(Set.of("ADMIN", "USER", "GUEST")),
  GENDER(Set.of("MALE", "FEMALE", "OTHER")),
  PHONE_TYPE(Set.of("MOBILE", "HOME", "WORK"));

  private Set<String> values;

  Field(Set<String> value) {}

  public Set<String> getValues() {
    return values;
    }
}
