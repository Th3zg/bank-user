package com.user_services.user_services.services.interfaces;

import com.user_services.user_services.dto.request.CreateUserRequest;
import com.user_services.user_services.util.Result;

public interface UserServices {
  Result<?> getAllCustomers();
  Result<?> getCustomerById();
  Result<?> createCustomer(CreateUserRequest request);
  Result<?> updateCustomer();
  Result<?> deleteCustomer();
  Result<?> searchCustomers();
}