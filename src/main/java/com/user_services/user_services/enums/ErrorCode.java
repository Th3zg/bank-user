package com.user_services.user_services.enums;

public enum ErrorCode {
  /* =====================================
   * CLIENT ERRORS (4xx)
   * ===================================== */

  // Validation and format
  INVALID_INPUT(400, "Invalid input data"),
  INVALID_REQUEST(400, "Malformed request format"),
  FIELD_VALIDATION_FAILED(400, "One or more fields are invalid"),

  // Resource not found
  CLIENT_NOT_FOUND(404, "Client not found"),
  RESOURCE_NOT_FOUND(404, "Requested resource not found"),

  // Authentication/Authorization
  UNAUTHORIZED(401, "Authentication required"),
  FORBIDDEN(403, "Access denied"),
  INVALID_CREDENTIALS(401, "Invalid username or password"),
  TOKEN_EXPIRED(401, "Authentication token expired"),

  // Conflicts
  DUPLICATE_ENTRY(409, "Duplicate resource detected"),
  RESOURCE_ALREADY_EXISTS(409, "Resource already exists"),

  // Rate limits and restrictions
  TOO_MANY_REQUESTS(429, "Rate limit exceeded"),
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported content type"),

  /* =====================================
   * SERVER ERRORS (5xx)
   * ===================================== */

  // Database
  DATABASE_ERROR(500, "Internal database error"),
  DATABASE_CONNECTION_FAILED(503, "Database connection failed"),
  CONSTRAINT_VIOLATION(500, "Data integrity constraint violated"),

  // System
  INTERNAL_SERVER_ERROR(500, "Unexpected server error"),
  SERVICE_UNAVAILABLE(503, "Service temporarily unavailable"),
  GATEWAY_TIMEOUT(504, "External service timeout"),

  /* =====================================
   * BUSINESS ERRORS
   * ===================================== */

  PAYMENT_REQUIRED(402, "Payment required"),
  OPERATION_NOT_ALLOWED(403, "Operation not permitted"),
  INVALID_STATE_TRANSITION(409, "Invalid state change");

  private final int status;
  private final String message;

  ErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}