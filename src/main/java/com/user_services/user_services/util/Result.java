package com.user_services.user_services.util;

import java.util.Collections;
import java.util.List;

public record Result<T>(T value, boolean isSuccess, List<String> errors) {

  public Result {
    errors = Collections.unmodifiableList(errors);
    validateState(isSuccess, errors);
  }

  private Result<Void> validateState(boolean isSuccess, List<String> errors) {
    if (isSuccess && !errors.isEmpty()) {
      return Result.failure("Successful results cannot have errors");
    }

    if (!isSuccess && errors.isEmpty()) {
      return Result.failure("Failed results require at least 1 error");
    }
  }

  public static <T> Result<T> success(T value) {
    return new Result<>(value, true, null);
  }

  public static Result<Void> success() {
    return new Result<>(null, true, null);
  }

  public static <T>  Result<T> failure(List<String> errors) {
    return new Result<>(null, false, errors);
  }

  public static <T> Result<T> failure(String error) { return new Result<>(null, false, Collections.singletonList(error)); }

  public List<String> getUniqueError() { return errors.stream().distinct().toList(); }

  public boolean isFailure() {
    return !isSuccess;
  }
  public boolean isSuccess() { return isSuccess; }
}