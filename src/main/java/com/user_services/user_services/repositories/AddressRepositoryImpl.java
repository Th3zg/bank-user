package com.user_services.user_services.repositories;

import com.user_services.user_services.exception.DatabaseErrorExceptionMapper;
import com.user_services.user_services.model.entity.Address;
import com.user_services.user_services.repositories.interfaces.AddressRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
  private final Logger logger = LoggerFactory.getLogger(AddressRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  public Try<Void> create(Address address) {
    String sql = "INSERT INTO address (person_id, street, street_number, apartment_number, neighborhood, city, state, postal_code, country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    return Try.run(() -> {
      jdbcTemplate.update(sql,
              address.getPersonId(),
              address.getStreet(),
              address.getStreetNumber(),
              address.getApartmentNumber(),
              address.getNeighborhood(),
              address.getCity(),
              address.getState(),
              address.getPostalCode(),
              address.getCountryCode()
      );
      logger.info("Address created successfully");
    }).onFailure(ex -> {
      logger.error("Error creating address", ex);
      DatabaseErrorExceptionMapper.fromException((DataAccessException) ex);
    });
  }
}
