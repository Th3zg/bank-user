package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.model.Client;
import io.vavr.control.Try;

public interface UserRepository {
  Try<Void> create(Client client);
}
