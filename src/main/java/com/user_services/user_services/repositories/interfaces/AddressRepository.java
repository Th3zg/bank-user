package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.model.entity.Address;
import io.vavr.control.Try;

public interface AddressRepository {
  Try<Void> create(Address address);
}
