package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.outbox.OutboxEvent;
import io.vavr.control.Try;

import java.util.List;

public interface OutboxRepository {
  List<OutboxEvent> findByProcessedFalse();
  Try<Void> markAsProcessed(Long id);
  Try<Void> insert(OutboxEvent event);
  Try<List<OutboxEvent>> findPendingEvents();
}
