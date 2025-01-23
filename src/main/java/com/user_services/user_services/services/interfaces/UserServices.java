package com.user_services.user_services.services.interfaces;

import com.user_services.user_services.dto.request.CreateUserRequest;
import com.user_services.user_services.util.Result;

public interface UserServices {
  Result<?> getAllUsers();
  Result<?> getUserById();
  Result<?> createUser(CreateUserRequest request);
  Result<?> updateUser();
  Result<?> deleteUser();
  Result<?> searchUsers();
}