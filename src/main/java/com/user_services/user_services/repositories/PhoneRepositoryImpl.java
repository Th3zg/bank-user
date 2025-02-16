package com.user_services.user_services.repositories;

import com.user_services.user_services.exception.DatabaseErrorExceptionMapper;
import com.user_services.user_services.model.entity.Phone;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class PhoneRepositoryImpl {
  private final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  public Try<Void> create(Phone phone) {
    String sql = "INSERT INTO phones (person_id, phone_number, phone_type) VALUES (?, ?, ?)";

    return Try.run(() -> {
      jdbcTemplate.update(sql,
              phone.getPersonId(),
              phone.getPhoneNumber(),
              phone.getPhoneType()
      );
      logger.info("Phone created successfully");
    }).onFailure(ex -> {
      logger.error("Error creating phone", ex);
      DatabaseErrorExceptionMapper.fromException((DataAccessException) ex);
    });
  }
}
