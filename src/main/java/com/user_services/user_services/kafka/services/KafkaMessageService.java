package com.user_services.user_services.kafka.services;

import com.user_services.user_services.kafka.interfaces.KafkaSendCallback;
import com.user_services.user_services.outbox.OutboxEvent;
import com.user_services.user_services.repositories.OutboxRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageService implements KafkaSendCallback {
  private final Logger logger = LoggerFactory.getLogger(KafkaMessageService.class);

  private final OutboxRepositoryImpl outboxRepository;

  public void onSuccess(OutboxEvent event, SendResult<String, OutboxEvent> result) { // logeo, metricas,etc
    logger.info("Successfully sent event {} to Kafka.", event.id());
    result.getRecordMetadata();
  }

  public void onFailure(OutboxEvent event, ProducerRecord<String, OutboxEvent> record, Throwable ex) {
    logger.error("Failed to send event {} to Kafka topic {}. Exception: {}", event.id(), record.topic(), ex.getMessage(), ex);
  }
}
