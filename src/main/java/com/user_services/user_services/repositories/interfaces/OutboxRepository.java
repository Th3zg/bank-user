package com.user_services.user_services.repositories.interfaces;

import com.user_services.user_services.outbox.OutboxEvent;

import java.util.List;

public interface OutboxRepository {
  List<OutboxEvent> findByProcessedFalse();
  void markAsProcessed(Long id);
  void insert(OutboxEvent event);
  List<OutboxEvent> findPendingEvents();
}
