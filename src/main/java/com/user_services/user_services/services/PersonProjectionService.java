package com.user_services.user_services.services;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.user_services.user_services.events.PersonCreatedEvent;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonProjectionService {
  private final Logger logger = LoggerFactory.getLogger(PersonProjectionService.class);
  private final Collection collection;

  public void CreatePersonDocument(PersonCreatedEvent event) {
    JsonObject document = JsonObject.create()
            .put("type", "person")
            .put("person_id", event.personId())
            .put("first_name", event.firstName())
            .put("last_name", event.lastName())
            .put("email", event.email())
            .put("date_of_birth", event.dateOfBirth().toString())
            .put("gender", event.gender())
            .put("created_at", event.createdAt().toString());

    String id = "person::" + event.personId();
    Try.run(() -> collection.upsert(id, document))
            .onFailure(this::handleCouchbaseError);
  }

  private void handleCouchbaseError(Throwable throwable) {
    logger.error("Error al proyectar documento en Couchbase: {}", throwable.getMessage(), throwable);
    // alertas, reintentos, etc. (pendiente)
  }
}
