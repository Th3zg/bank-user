package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.domain.Person;
import io.vavr.control.Try;

public interface PersonRepository {
  Try<Long> create(Person person);
}