package com.user_services.user_services.exception;

import com.user_services.user_services.dto.error.ErrorResponse;
import com.user_services.user_services.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public void handleGenericHandle() {
  }

  // 2. deserialization Errors (400 Bad Request)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleValidationExceptions(MethodArgumentNotValidException ex) {
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

  private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode, String message, String providedValue, Set<String> acceptedValues) {
    ErrorResponse errorResponse = new ErrorResponse(
            errorCode,
            null,
            message,
            new ErrorResponse.ErrorDetails(providedValue, acceptedValues),
            HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

  }
}
