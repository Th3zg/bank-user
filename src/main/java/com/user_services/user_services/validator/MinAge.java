package com.user_services.user_services.validator;

import jakarta.validation.Payload;

public @interface MinAge {
  String message() default "Customer must be at least 18 years old";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int value() default 18;
}