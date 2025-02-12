package com.user_services.user_services.model.document;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Getter
public class AddressDocument {
  @Id
  private String id;
  @Field
  private Long addressId;
  @Field
  private Long personId;
  @Field
  private String street;
  @Field
  private String streetNumber;
  @Field
  private String apartmentNumber;
  @Field
  private String neighborhood;
  @Field
  private String city;
  @Field
  private String state;
  @Field
  private String postalCode;
  @Field
  private String countryCode;
  @Field
  private String createdAt;
  @Field
  private String updatedAt;
}
