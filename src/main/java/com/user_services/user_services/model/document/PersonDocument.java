package com.user_services.user_services.model.document;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Getter
public class PersonDocument {
  @Id
  private String id;
  @Field
  private Long personId;
  @Field
  private String firstName;
  @Field
  private String lastName;
  @Field
  private String email;
  @Field
  private String dateBirth;
  @Field
  private String gender;
  @Field
  private String profileImageUrl;
  @Field
  private String bio;
  @Field
  private String communicationPreference;
  @Field
  private Boolean termsAccepted;
  @Field
  private String createdAt;
  @Field
  private String updatedAt;
}
