package com.user_services.user_services.repositories;

import com.user_services.user_services.exception.DatabaseErrorExceptionMapper;
import com.user_services.user_services.model.entity.Phone;
import com.user_services.user_services.repositories.interfaces.PhoneRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhoneRepositoryImpl implements PhoneRepository {
  private final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  public Try<Phone> create(Phone phone) {
    String sql = """
            INSERT INTO phones (
            person_id, phone_number, phone_type
            ) VALUES (?, ?, ?)
            RETURNING person_id, phone_number, phone_type
            """;

    return Try.of(() -> {
      jdbcTemplate.update(sql,
              phone.getPersonId(),
              phone.getPhoneNumber(),
              phone.getPhoneType()
      );
      logger.info("Phone created successfully");
      return phone;
    }).onFailure(ex -> {
      logger.error("Error creating phone", ex);
      DatabaseErrorExceptionMapper.fromException((DataAccessException) ex);
    });
  }
}
