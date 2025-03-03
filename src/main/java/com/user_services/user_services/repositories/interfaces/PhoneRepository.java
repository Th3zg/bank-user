package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.model.entity.Phone;
import io.vavr.control.Try;

public interface PhoneRepository {
  Try<Phone> create(Phone phone);
}
