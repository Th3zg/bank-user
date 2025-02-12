package com.user_services.user_services.model.document;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
@Getter
public class ClientDocument {
  @Id
  private String id;
  @Field
  private Long clientId;
  @Field
  private Long personId;
  @Field
  private String firstName;
  @Field
  private String lastName;
  @Field
  private String email;
  @Field
  private String accountNumber;
  @Field
  private Double accountBalance;
  @Field
  private Double overdraftLimit;
  @Field
  private Integer creditScore;
  @Field
  private Double monthlyIncome;
  @Field
  private String occupation;
  @Field
  private String maritalStatus;
  @Field
  private Double totalLoans;
  @Field
  private Double totalInvestments;
  @Field
  private Double totalInsurance;
  @Field
  private String createdAt;
  @Field
  private String updatedAt;
}
