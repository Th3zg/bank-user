package com.user_services.user_services.repositories;

import com.user_services.user_services.enums.AggregateType;
import com.user_services.user_services.enums.EventType;
import com.user_services.user_services.enums.OutboxStatus;
import com.user_services.user_services.outbox.OutboxEvent;
import com.user_services.user_services.repositories.interfaces.OutboxRepository;
import com.user_services.user_services.util.Json;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
  private final Logger logger = LoggerFactory.getLogger(OutboxRepositoryImpl.class);
  private final JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public List<OutboxEvent> findByProcessedFalse() {
    return List.of();
  }

  @Override
  public Try<Void> markAsProcessed(Long id) {
    String sql = """
            UPDATE outbox_events
            SET status = 'PROCESSED'
            WHERE id = ?
            """;

    return Try.run(() -> {
      jdbcTemplate.update(sql, id);
    });
  }

  @Override
  public Try<Void> insert(OutboxEvent event) {
    String sql = """
            INSERT INTO outbox_events (aggregate_type, aggregate_id, type, data, status)
            VALUES (?, ?, ?, ?::jsonb), ?)
        """;

    String dataJson = Json.convertPayloadToJson(event.data());

    return Try.run(() -> {
      jdbcTemplate.update(sql,
              event.aggregate_type(),
              event.aggregate_id(),
              event.type(),
              event.data(),
              event.status());
    });
  }

  @Override
  public Try<List<OutboxEvent>> findPendingEvents() {
    String sql = """
            SELECT id,
                   aggregate_type,
                   aggregate_id,
                   event_type,
                   data,
                   status
            FROM outbox_events
            WHERE status = PENDING
            ORDER BY created_at ASC
            """;

    return Try.of(() ->
            jdbcTemplate.query(sql, (rs, rowNum) -> new OutboxEvent(
                    rs.getLong("id"),
                    AggregateType.valueOf(rs.getString("aggregate_type")),
                    rs.getLong("aggregate_id"),
                    EventType.valueOf(rs.getString("type")),
                    rs.getObject("data"),
                    OutboxStatus.valueOf(rs.getString("status")),
                    null,
                    0,
                    null,
                    null,
                    null
            ))
    )
    .onFailure(err -> logger.error("Technical error in OutboxRepository {}", err.getMessage()));
  }
}
