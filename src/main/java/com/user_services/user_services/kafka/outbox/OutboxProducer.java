package com.user_services.user_services.kafka.outbox;

import com.user_services.user_services.kafka.services.KafkaMessageService;
import com.user_services.user_services.kafka.services.KafkaProducerService;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxProducer { // Producer
  private final Logger logger  = LoggerFactory.getLogger(OutboxProducer.class);
  private final OutboxRepositoryImpl outboxRepositoryImpl;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final TransactionTemplate transactionTemplate;
  private final KafkaMessageService kafkaMessageService;
  private final KafkaProducerService kafkaProducerService;

  @Scheduled(fixedDelay = 5000)
  public Result<Void> processOutboxEvents() {
    return outboxRepositoryImpl.findPendingEvents() // agregar, quizas, un limite para no sobrecargar.
            .fold(error -> {
                      logger.error("Error fetching events", error);
                      return Result.failure("Fetch error: " + error.getMessage());
                    },
                    this::processEvents);
  }

  private Result<Void> processEvents(List<OutboxEvent> events) {
    List<Try<Void>> processingResults = events.stream()
            .map(this::processSingleEvent)
            .toList();

    boolean anyFailure = processingResults.stream().anyMatch(Try::isFailure);
    if (anyFailure) {
      return Result.failure(processingResults.stream()
              .filter(Try::isFailure)
              .map(t -> t.getCause().getMessage())
              .toList());
    }
    return Result.success();
  }

  private Try<Void> processSingleEvent(final OutboxEvent event) {
    return Try.run(() ->
            kafkaProducerService.sendEvent(event))
            .onFailure(ex -> logger.error("Failed to send event: {}", event.id(), ex));
  }
}
