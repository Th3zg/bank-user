package com.user_services.user_services.repositories;

import com.user_services.user_services.domain.Person;
import com.user_services.user_services.repositories.interfaces.PersonRepository;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
  private final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);

  @Override
  public Try<Long> create(Person person) {
    String sql = "INSERT INTO persons ()";

    return Try.of(() -> 2l);
  }
}