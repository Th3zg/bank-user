package com.user_services.user_services.command.handler;

import com.user_services.user_services.command.CreateAddressCommand;
import com.user_services.user_services.model.entity.Address;
import com.user_services.user_services.repositories.AddressRepositoryImpl;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class AddressCommandHandler {
  private final Logger logger = LoggerFactory.getLogger(AddressCommandHandler.class);
  private final AddressRepositoryImpl addressRepository;
  private final TransactionTemplate transactionTemplate;

  public Try<Address> handler(Long personId, CreateAddressCommand command) {
    return transactionTemplate.execute(status -> {
      return Try.of(() -> createAddress(personId, command))
              .flatMap(address -> persistAddress(address, status))
              .map(address -> {
                        logger.info("Client created successfully: {}", address.getId());
                        return address;
                      });
    });
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

  private Try<Void> persistAddress(Address address, TransactionStatus status) {
    return addressRepository.create(address)
            .onFailure(err -> {
              logger.error("Error creating address: {}", err.getMessage());
              status.setRollbackOnly();
            });
  }
}
