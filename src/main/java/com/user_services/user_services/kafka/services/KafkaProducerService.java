package com.user_services.user_services.kafka.services;

import com.user_services.user_services.outbox.OutboxEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
  private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
  private final KafkaTemplate<String, OutboxEvent> kafkaTemplate;
  private final KafkaMessageService kafkaMessageService;
  private final Retry kafkaRetry;
  private final CircuitBreaker kafkaCircuitBreaker;

  public void sendEvent(OutboxEvent event) {
    ProducerRecord<String, OutboxEvent> record = new ProducerRecord<>(String.valueOf(event.type()), event);

    Runnable sendTask = () -> kafkaTemplate.send(record)
            .whenComplete((result, ex) -> {
              if (ex == null) {
                kafkaMessageService.onSuccess(event, result);
              } else {
                kafkaMessageService.onFailure(event, record, ex);
              }
            });

    Runnable decorated = CircuitBreaker.decorateRunnable(kafkaCircuitBreaker, Retry.decorateRunnable(kafkaRetry, sendTask));
    decorated.run();
  }
}
