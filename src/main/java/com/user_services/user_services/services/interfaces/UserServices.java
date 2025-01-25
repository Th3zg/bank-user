package com.user_services.user_services.services.interfaces;

import com.user_services.user_services.command.CreateUserCommand;
import com.user_services.user_services.util.Result;

public interface UserServices {
  Result<?> getAllUsers();
  Result<?> getUserById();
  Result<?> createUser(CreateUserCommand request);
  Result<?> updateUser();
  Result<?> deleteUser();
  Result<?> searchUsers();
}