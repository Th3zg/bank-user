package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.model.entity.Person;
import io.vavr.control.Try;

public interface PersonRepository {
  Try<Person> create(Person person);
}