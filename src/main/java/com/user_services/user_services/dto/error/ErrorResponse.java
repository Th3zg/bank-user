package com.user_services.user_services.dto.error;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class ErrorResponse {
  private final String errorCode;
  private final Set<String> messages;
  private final String message;
  private final ErrorDetails details;
  private final LocalDateTime timestamp;

  public ErrorResponse(String errorCode, Set<String> messages, String message, ErrorDetails details) {
    this.errorCode = errorCode;
    this.messages = messages;
    this.message = message;
    this.details = details;
    this.timestamp = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
            "errorCode='" + errorCode + '\'' +
            ", messages='" + messages + '\'' +
            ", message='" + message + '\'' +
            ", details=" + details +
            ", timestamp=" + timestamp +
            '}';
  }

  public static class ErrorDetails {
    private final String providedValue;
    private final Set<String> acceptedValues;

    public ErrorDetails(String providedValue, Set<String> acceptedValues) {
      this.providedValue = providedValue;
      this.acceptedValues = acceptedValues;
    }

    @Override
    public String toString() {
      return "ErrorDetails{" +
              "providedValue='" + providedValue + '\'' +
              ", acceptedValues=" + acceptedValues +
              '}';
    }
  }
}