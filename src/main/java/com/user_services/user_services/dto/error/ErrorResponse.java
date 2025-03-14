package com.user_services.user_services.dto.error;

import com.user_services.user_services.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorResponse(
        ErrorCode errorCode,
        Set<String> messages, 
        String message,
        ErrorDetails details,
        LocalDateTime timestamp,
        int statusCode
) {

  public ErrorResponse( ErrorCode errorCode, Set<String> messages, String message, ErrorDetails details, int statusCode) {
    this(errorCode, messages, message, details, LocalDateTime.now(), statusCode);
  }
  public record ErrorDetails(
          String providedValue,
          Set<String> acceptedValues
  ) {}
}