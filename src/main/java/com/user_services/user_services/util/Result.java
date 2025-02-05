package com.user_services.user_services.util;

import java.util.Set;

public record Result<T>(T value, boolean isSuccess, Set<String> errors) {
  public static <T> Result<T> success(T value) {
    return new Result<>(value, true, null);
  }

  public static Result<Void> success() {
    return new Result<>(null, true, null);
  }

  public static Result<Void> failure(Set<String> errors) {
    return new Result<>(null, false, errors);
  }

  public boolean isFailure() {
    return !isSuccess;
  }
}