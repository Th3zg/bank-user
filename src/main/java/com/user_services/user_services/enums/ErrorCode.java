package com.user_services.user_services.enums;

public enum ErrorCode {
  CLIENT_NOT_FOUND(404, "Client not found"),
  INVALID_INPUT(400, "Invalid input provided"),
  INVALID_REQUEST(400, "Invalid request format or data"),

  // Database errors
  DATABASE_ERROR(500, "Database error occurred"),
  DUPLICATE_ENTRY(409, "Duplicate entry detected"),
  RESOURCE_NOT_FOUND(404, "Resource not found in database"),

  // Authentication/Authorization Errors
  UNAUTHORIZED(401, "Unauthorized access"),
  FORBIDDEN(403, "Forbidden operation");

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