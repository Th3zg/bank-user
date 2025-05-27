package com.user_services.user_services.kafka.consumer;

import com.couchbase.client.java.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_services.user_services.events.PersonCreatedEvent;
import com.user_services.user_services.services.PersonProjectionService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import com.couchbase.client.java.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonKafkaConsumer {
  private final Logger logger = LoggerFactory.getLogger(PersonKafkaConsumer.class);
  private final ObjectMapper objectMapper;
  private final PersonProjectionService personProjectionService;

  @KafkaListener(topics = "PersonCreatedEvent", groupId = "couchbase-sync-group")
  public void handlePersonCreatedEvent(String eventJsonb) {
    Try.of(() -> objectMapper.readValue(eventJsonb, PersonCreatedEvent.class))
            .onSuccess(event -> {
              personProjectionService.CreatePersonDocument(event);
              logger.info("Person document synchronized in Couchbase: {}", event.personId());
            }).onFailure(e -> logger.error("Error deserializing PersonCreatedEvent: {}", e.getMessage(), e));
  }
}
