package com.user_services.user_services.util;

import java.util.*;
import java.util.function.Supplier;

public class ValidationUtils {

  public static <T> Result<T> validateFields(Map<String, Object> fields, Supplier<T> objectCreator) {
    Set<String> errors = new HashSet<>();
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      if (entry.getValue() == null) {
        errors.add(entry.getKey() + " is required");
      }
    }
    if (!errors.isEmpty()) {
      return Result.failure(errors);
    }
    return Result.success(objectCreator.get());
  }
}