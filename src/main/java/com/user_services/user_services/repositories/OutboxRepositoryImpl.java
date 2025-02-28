package com.user_services.user_services.repositories;

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
            INSERT INTO outbox_events (aggregate_type, aggregate_id, type, payload, status, created_at)
            VALUES (?, ?, ?, ?::jsonb), ?, ?)
        """;

    String payloadJson = Json.convertPayloadToJson(event.payload());

    return Try.run(() -> {
      jdbcTemplate.update(sql,
              event.aggregate_type(),
              event.aggregate_id(),
              event.type(),
              event.payload(),
              event.status());
    });
  }

  @Override
  public Try<List<OutboxEvent>> findPendingEvents() {
    String sql = """
            SELECT * FROM outbox_events
            WHERE status = ?
            ORDER BY created_at ASC
            """;

    return Try.of(() ->
            jdbcTemplate.query(sql, (rs, rowNum) -> new OutboxEvent(
                    rs.getLong("id"),
                    rs.getString("aggregate_type"),
                    rs.getLong("aggregate_id"),
                    rs.getString("type"),
                    rs.getObject("payload"),
                    OutboxStatus.valueOf(rs.getString("status"))
            ))
    );
  }
}
