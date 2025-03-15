package com.user_services.user_services.dto.error;

import com.user_services.user_services.enums.ErrorCode;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorResponse(
        ErrorCode errorCode,
        Set<String> messages, 
        ErrorDetails details,
        LocalDateTime timestamp
) {

  public ErrorResponse(ErrorCode errorCode, Set<String> messages, ErrorDetails details) {
    this(errorCode, messages, details, LocalDateTime.now());
  }

  public ErrorResponse(ErrorCode errorCode, Set<String> messages) {
    this(errorCode, messages, null, LocalDateTime.now());
  }

  public ErrorResponse(ErrorCode errorCode) {
    this(errorCode, null, null, LocalDateTime.now());
  }

  public record ErrorDetails(
          String providedValue,
          Set<String> acceptedValues
  ) {}
}