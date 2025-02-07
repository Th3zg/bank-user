package com.user_services.user_services.repositories;

import com.user_services.user_services.exception.DatabaseErrorExceptionMapper;
import com.user_services.user_services.model.Person;
import com.user_services.user_services.repositories.interfaces.PersonRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
  private final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Try<Long> create(Person person) {
    String sql = "INSERT INTO persons (first_name, last_name, date_birth, email, password, gender, profile_image_url, communication_preference, terms_accepted, bio) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING person_id";

    return Try.of(() -> {
      Long personId = jdbcTemplate.queryForObject(
              sql,
              Long.class,
              person.getFirstName(),
              person.getLastName(),
              person.getDateBirth(),
              person.getEmail(),
              person.getPassword(),
              person.getGender().name(),
              person.getProfileImageUrl(),
              person.getCommunicationPreference() != null ? person.getCommunicationPreference().name() : null,
              person.isTermsAccepted(),
              person.getBio()
      );
      logger.info("Person created with ID: {}", personId);
      return personId;
    }).onFailure(ex -> {
      logger.error("Error creating person", ex);
      DatabaseErrorExceptionMapper.fromException((DataAccessException) ex);
    });
  }
}