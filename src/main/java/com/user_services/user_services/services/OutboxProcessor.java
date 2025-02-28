package com.user_services.user_services.services;

import com.user_services.user_services.outbox.OutboxEvent;
import com.user_services.user_services.repositories.OutboxRepositoryImpl;
import com.user_services.user_services.util.Result;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxProcessor {
  private final Logger logger  = LoggerFactory.getLogger(OutboxProcessor.class);
  private final OutboxRepositoryImpl outboxRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final TransactionTemplate transactionTemplate;

  @Scheduled(fixedDelay = 5000)
  public Result<Void> processOutboxEvents() {
    return transactionTemplate.execute(status -> {
      Try<List<OutboxEvent>> outboxLists = outboxRepository.findPendingEvents();

      if (outboxLists.isFailure()) {
        status.setRollbackOnly();
        return Result.failure(Collections.singleton("Error: " + outboxLists.getCause().getMessage()));
      }

      List<OutboxEvent> events = outboxLists.get();
      events.forEach(event -> {
        kafkaTemplate.send(event.type(), event.payload());
        outboxRepository.markAsProcessed(event.id());
      });
      return Result.success();
    });
  }
}
