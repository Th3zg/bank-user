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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

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
    return transactionTemplate.execute(status ->
            outboxRepository.findPendingEvents()
                    .fold(error -> {
                      logger.error("Error fetching events", error);
                      status.setRollbackOnly();
                      return Result.failure("Fetch error: " + error.getMessage());
                            },
                            events -> processEvents(events, status)));
  }

  private Result<Void> processEvents(List<OutboxEvent> events, TransactionStatus status) {
    List<Try<Void>> processingResults = events.stream()
            .map(this::processSingleEvent)
            .toList();

    List<String> errors = processingResults.stream()
            .filter(Try::isFailure)
            .map(e -> e.getCause().getMessage())
            .toList();

    if (errors.isEmpty()) {
      status.setRollbackOnly();
      return Result.failure(errors);
    }

    return Result.success();
  }

  private Try<Void> processSingleEvent(OutboxEvent event) {
    return Try.run(() ->
            kafkaTemplate.send(event.type(), event.payload()).get()
    )
    .flatMap((v) -> outboxRepository.markAsProcessed(event.id()))
    .onFailure(ex ->
            logger.error("Failed processing event {}", event.id(), ex));
  }
}
