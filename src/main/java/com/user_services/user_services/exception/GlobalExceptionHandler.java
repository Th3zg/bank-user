package com.user_services.user_services.exception;

import com.user_services.user_services.dto.error.ErrorResponse;
import com.user_services.user_services.enums.ErrorCode;
import com.user_services.user_services.enums.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericHandle(Exception ex) {
    logger.error("Unexpected error: ", ex);
    return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, Set.of(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()), null, null);
  }

  // 2. deserialization Errors (400 Bad Request)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    logger.warn("Invalid request format: {}", ex.getMessage());
    return createErrorResponse(
            ErrorCode.INVALID_REQUEST,
            Set.of(ErrorCode.INVALID_REQUEST.getMessage()),
            extractInvalidValue(ex.getMessage()),
            getAcceptedValues(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Set<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage).collect(Collectors.toSet());

    return createErrorResponse(ErrorCode.FIELD_VALIDATION_FAILED, errorMessages, null, null);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
    logger.error("Database error: ", ex);
    return createErrorResponse(ErrorCode.DATABASE_ERROR, Set.of(ErrorCode.DATABASE_ERROR.getMessage()), null, null);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    logger.error("integrity violation: ", ex);
    return createErrorResponse(ErrorCode.DUPLICATE_ENTRY, Set.of(ErrorCode.DUPLICATE_ENTRY.getMessage()), extractConstraintValue(ex), null);
  }

  @ExceptionHandler(DataAccessResourceFailureException.class)
  public ResponseEntity<ErrorResponse> handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) {
    logger.error("Database connection failed: ", ex);
    return createErrorResponse(ErrorCode.DATABASE_CONNECTION_FAILED, Set.of(ErrorCode.DATABASE_CONNECTION_FAILED.getMessage()), null, null);
  }

  @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
  public ResponseEntity<ErrorResponse> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex) {
    logger.error("Query result size error: ", ex);
    return createErrorResponse(ErrorCode.RESOURCE_NOT_FOUND, Set.of(ErrorCode.RESOURCE_NOT_FOUND.getMessage()), null, null);
  }

  @ExceptionHandler(UncategorizedSQLException.class)
  public ResponseEntity<ErrorResponse> handleUncategorizedSQLException(UncategorizedSQLException ex) {
    logger.error("Uncategorized SQL error: ", ex);
    return createErrorResponse(
            ErrorCode.SQL_ERROR,
            Set.of(ErrorCode.SQL_ERROR.getMessage()),
            extractSqlErrorMessage(ex),
            null
    );
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode, Set<String> messages, String providedValue, Set<String> acceptedValues) {
    ErrorResponse errorResponse = new ErrorResponse(
            errorCode,
            messages,
            new ErrorResponse.ErrorDetails(providedValue, acceptedValues)
    );

    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  private String extractInvalidValue(String errorMessage) {
    if (errorMessage == null) return "unknown";

    Pattern pattern = Pattern.compile("'(.*?)'|\"(.*?)\"");
    Matcher matcher = pattern.matcher(errorMessage);

    return matcher.find() ? (matcher.group(1) != null ? matcher.group(1) : matcher.group(2)) : "unknown";
  }

  private Set<String> getAcceptedValues(String errorMessage) {
    String field = extractFieldName(errorMessage).toUpperCase();

    return switch (field) {
      case "ROLE" -> Field.ROLE.getValues();
      case "GENDER" -> Field.GENDER.getValues();
      case "PHONE_TYPE" -> Field.PHONE_TYPE.getValues();
      default -> Set.of();
    };
  }

  private String extractConstraintValue(DataIntegrityViolationException ex) {
    String message = ex.getMessage();
    if (message.contains("Duplicate entry")) {
      return message.split("'")[1];
    }
    return "unknown_field";
  }

  private String extractFieldName(String errorMessage) {
    if (errorMessage == null) return "unknown";

    Pattern pattern = Pattern.compile("(?i)(field|property|type)\\s*['\"]?(\\w+)['\"]?");
    Matcher matcher = pattern.matcher(errorMessage);

    return matcher.find() ? matcher.group(2) : "unknown";
  }

  private String extractSqlErrorMessage(UncategorizedSQLException ex) {
    SQLException sqlException = ex.getSQLException();

    return sqlException != null ? sqlException.getMessage() : "Unknown SQL error";
  }
}
