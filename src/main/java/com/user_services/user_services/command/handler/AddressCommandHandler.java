package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreateAddressCommand;
import com.user_services.user_services.model.entity.Address;
import com.user_services.user_services.repositories.AddressRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(AddressCommandHandler.class);
  private final AddressRepositoryImpl addressRepository;

  public Result<Void> handler(Long personId, CreateAddressCommand command) {
    // create the address
    Address address = createAddress(personId, command);

    Try<Void> resultAddressCreation = addressRepository.create(address);
    if (resultAddressCreation.isFailure()) {
      return Result.failure("Error: " + resultAddressCreation.getCause().getMessage());
    }
    return Result.success();
  }

  private Address createAddress(long personId, CreateAddressCommand command) {
    return new Address.Builder()
            .setPersonId(personId)
            .setStreet(command.street())
            .setStreetNumber(command.streetNumber())
            .setApartmentNumber(command.apartmentNumber())
            .setNeighborhood(command.neighborhood())
            .setCity(command.city())
            .setState(command.state())
            .setPostalCode(command.postalCode())
            .setCountryCode(command.country())
            .build()
            .value();
  }
}
