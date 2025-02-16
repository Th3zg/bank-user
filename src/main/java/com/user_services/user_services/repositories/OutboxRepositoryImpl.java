package com.user_services.user_services.repositories;

import com.user_services.user_services.outbox.OutboxEvent;
import com.user_services.user_services.repositories.interfaces.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
  private final Logger logger = LoggerFactory.getLogger(OutboxRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<OutboxEvent> findByProcessedFalse() {
    return List.of();
  }

  @Override
  public void markAsProcessed(Long id) {

  }
}
