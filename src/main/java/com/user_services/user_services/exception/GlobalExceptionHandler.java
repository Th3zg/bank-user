package com.user_services.user_services.exception;

import com.user_services.user_services.dto.error.ErrorResponse;
import com.user_services.user_services.enums.ErrorCode;
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
  public void handleDataAccessException(DataAccessException ex) {
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
  }

  @ExceptionHandler(DataAccessResourceFailureException.class)
  public void handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) {
  }

  @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
  public void handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex) {
  }

  @ExceptionHandler(UncategorizedSQLException.class)
  public void handleUncategorizedSQLException(UncategorizedSQLException ex) {
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
    String field = extractInvalidValue(errorMessage);

    if (errorMessage.contains("Role")) {
      return Set.of("ADMIN", "USER", "GUEST");
    } else if (errorMessage.contains("Gender")) {
      return Set.of("MALE", "FEMALE", "OTHER");
    }
    return Set.of();
  }
}
