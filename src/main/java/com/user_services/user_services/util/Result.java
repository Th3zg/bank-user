package com.user_services.user_services.util;

import lombok.Getter;

import java.util.Set;

public class Result<T> {
  @Getter
  private final T value;
  private final boolean isSuccess;
  @Getter
  private final Set<String> errors;

  private Result(T value, boolean isSuccess, Set<String> errors ) {
    this.value = value;
    this.isSuccess = isSuccess;
    this.errors = errors;
  }

  public static <T> Result<T> success(T value) {
    return new Result<>(value, true, null);
  }

  public static Result<Void> success() {
    return new Result<>(null, true, null);
  }

  public static Result<Void> failure(Set<String> errors) {
    return new Result<>(null, false, errors);
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public boolean isFailure() {
    return !isSuccess;
  }

  @Override
  public String toString() {
    return "Result{" +
            "value=" + value +
            ", isSuccess=" + isSuccess +
            ", errors=" + errors +
            '}';
  }
}