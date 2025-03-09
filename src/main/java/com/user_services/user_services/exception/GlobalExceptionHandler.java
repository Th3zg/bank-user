package com.user_services.user_services.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public void handleGenericHandle() {}

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleValidationExceptions(MethodArgumentNotValidException ex) {}
}
