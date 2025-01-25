package com.user_services.user_services.domain;

import com.user_services.user_services.enums.CommunicationPreference;
import com.user_services.user_services.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Person {
  private final Long id;
  private final String firstName;
  private final String lastName;
  private final LocalDate dateBirth;
  private final String email;
  private final String password;
  private final Gender gender;
  private final String profileImageUrl;
  private final CommunicationPreference communicationPreference;
  private final String bio;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public Person(Builder builder) {
    this.id = builder.id;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.dateBirth = builder.dateBirth;
    this.email = builder.email;
    this.password = builder.password;
    this.gender = builder.gender;
    this.profileImageUrl = builder.profileImageUrl;
    this.communicationPreference = builder.communicationPreference;
    this.bio = builder.bio;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  public static class Builder {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateBirth;
    private String email;
    private String password;
    private Gender gender;
    private String profileImageUrl;
    private CommunicationPreference communicationPreference;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Builder setId(Long id) { this.id = id; return this; }
    public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
    public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
    public Builder setDateBirth(LocalDate dateBirth) { this.dateBirth = dateBirth; return this; }
    public Builder setEmail(String email) { this.email = email; return this; }
    public Builder setPassword(String password) { this.password = password; return this; }
    public Builder setGender(Gender gender) { this.gender = gender; return this; }
    public Builder setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; return this; }
    public Builder setCommunicationPreference(CommunicationPreference communicationPreference) { this.communicationPreference = communicationPreference; return this; }
    public Builder setBio(String bio) { this.bio = bio; return this; }
    public Builder setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
    public Builder setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

    public Person build() {
      return new Person(this);
    }
  }

  public Long getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public LocalDate getDateBirth() { return dateBirth; }
  public String getEmail() { return email; }
  public String getPassword() { return password; }
  public Gender getGender() { return gender; }
  public String getProfileImageUrl() { return profileImageUrl; }
  public CommunicationPreference getCommunicationPreference() { return communicationPreference; }
  public String getBio() { return bio; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
}