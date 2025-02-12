package com.user_services.user_services.model.document;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Getter
public class PhoneDocument {
  @Id
  private String id;
  @Field
  private Long phoneId;
  @Field
  private Long personId;
  @Field
  private String phoneNumber;
  @Field
  private String phoneType;
  @Field
  private String createdAt;
  @Field
  private String updatedAt;
}
