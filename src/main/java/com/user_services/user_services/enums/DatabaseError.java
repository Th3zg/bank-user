package com.user_services.user_services.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.*;
import org.springframework.jdbc.BadSqlGrammarException;

@Getter
@RequiredArgsConstructor
public enum DatabaseError {
  DUPLICATE_ENTRY(
          "Uniqueness violation",
          DataIntegrityViolationException.class,
          "A record with this identifier already exists",
          409
  ),
  FOREIGN_KEY_VIOLATION(
          "Foreign key violation",
          DataIntegrityViolationException.class,
          "Referenced entity does not exist",
          400
  ),
  INVALID_SQL(
          "SQL syntax error",
          BadSqlGrammarException.class,
          "Malformed query or incorrect types",
          400
  ),
  DATABASE_BUSY(
          "Database busy",
          CannotAcquireLockException.class,
          "Could not acquire a database lock",
          503
  ),
  DEADLOCK(
          "Deadlock detected",
          DeadlockLoserDataAccessException.class,
          "Transaction lock conflict",
          409
  ),
  CONCURRENCY_CONFLICT(
          "Concurrency conflict",
          ConcurrencyFailureException.class,
          "Optimistic locking failure",
          409
  ),
  ACCESS_DENIED(
          "Access denied",
          PermissionDeniedDataAccessException.class,
          "Insufficient permissions",
          403
  ),
  CONNECTION_FAILURE(
          "Connection failure",
          DataAccessResourceFailureException.class,
          "Could not connect to the database",
          503
  ),
  INVALID_DATA_TYPE(
          "Invalid data type",
          TypeMismatchDataAccessException.class,
          "Incorrect data type mapping",
          400
  ),
  UNEXPECTED_DATABASE_ERROR(
          "Unexpected error",
          DataAccessException.class,
          "Internal system error",
          500
  );

  private final String title;
  private final Class<? extends DataAccessException> exceptionType;
  private final String userMessage;
  private final int httpStatus;
}