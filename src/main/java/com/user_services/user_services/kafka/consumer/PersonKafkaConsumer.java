package com.user_services.user_services.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_services.user_services.events.PersonCreatedEvent;
import com.user_services.user_services.services.PersonProjectionService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonKafkaConsumer {
  private final Logger logger = LoggerFactory.getLogger(PersonKafkaConsumer.class);
  private final ObjectMapper objectMapper;
  private final PersonProjectionService personProjectionService;
  private final Retry personProjectionRetry;
  private final CircuitBreaker personProjectionBreaker;


  // @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 1000))
  @KafkaListener(topics = "PersonCreatedEvent", groupId = "couchbase-sync-group")
  public void handlePersonCreatedEvent(String eventJsonb) {
    Try.of(() -> objectMapper.readValue(eventJsonb, PersonCreatedEvent.class))
            .onSuccess(event -> {
              Runnable projectionTask = () -> personProjectionService.CreatePersonDocument(event);
              Runnable resilientTask = CircuitBreaker.decorateRunnable(personProjectionBreaker, Retry.decorateRunnable(personProjectionRetry, projectionTask));
              resilientTask.run();
              logger.info("Person document synchronized in Couchbase: {}", event.personId());
              // ack.acknowledge(); // Confirmar offset manualmente
            }).onFailure(e -> logger.error("Error deserializing PersonCreatedEvent: {}", e.getMessage(), e));
  }

  @DltHandler
  public void handleFailedEvent(String eventJson) {
    logger.error("Mensaje enviado a DLT: {}", eventJson);
  }

}
