package com.user_services.user_services.services.interfaces;

import com.user_services.user_services.command.CreatePersonCommand;
import com.user_services.user_services.util.Result;

public interface ClientServices {
  Result<?> getAllClients();
  Result<?> getClientById();
  Result<?> createClient(CreatePersonCommand request);
  Result<?> updateClient();
  Result<?> deleteClient();
  Result<?> searchClients();
}