package com.user_services.user_services.exception;

import com.user_services.user_services.enums.DatabaseError;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;

public class DatabaseErrorExceptionMapper {
  public static DatabaseError fromException(DataAccessException ex) {
    if (ex instanceof DataIntegrityViolationException e) {
      return handleDataIntegrityViolation(e);
    }

    return Arrays.stream(DatabaseError.values())
            .filter(error -> error.getExceptionType().isInstance(ex))
            .findFirst()
            .orElse(DatabaseError.UNEXPECTED_DATABASE_ERROR);
  }

  private static DatabaseError handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    String message = ex.getMessage().toLowerCase();
    if (message.contains("duplicate") || message.contains("unique")) {
      return DatabaseError.DUPLICATE_ENTRY;
    }
    if (message.contains("foreign key")) {
      return DatabaseError.FOREIGN_KEY_VIOLATION;
    }
    return DatabaseError.UNEXPECTED_DATABASE_ERROR;
  }
}
