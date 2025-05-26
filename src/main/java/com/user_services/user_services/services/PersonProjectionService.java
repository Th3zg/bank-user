package com.user_services.user_services.services;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.user_services.user_services.events.PersonCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonProjectionService {
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
    collection.upsert(id, document);
  }
}
