package com.user_services.user_services.enums;

public enum ErrorCode {
  CLIENT_NOT_FOUND(404, "Client not found"),
  DATABASE_ERROR(500, "Database error occurred"),
  INVALID_INPUT(400, "Invalid input provided");

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}