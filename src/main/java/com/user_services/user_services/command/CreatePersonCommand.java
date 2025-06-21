package com.user_services.user_services.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_services.user_services.enums.CommunicationPreference;
import com.user_services.user_services.enums.Gender;
//import com.user_services.user_services.validator.MinAge;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreatePersonCommand(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name cannot exceed 50 characters")
        @JsonProperty("first_name") String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last nae cannot exceed 50 characters")
        @JsonProperty("last_name") String lastName,

        @NotNull(message = "Date of birth is required")
        //@MinAge(message = "Customer must be at least 18 years old")
        @JsonProperty("date_birth") LocalDate dateBirth,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @JsonProperty("email") String email,

        @NotBlank(message = "Password is required")
        @Size(min = 10, message = "Password must be at least 12 characters long")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$",
                message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
        @JsonProperty("password") String password,

        @NotNull(message = "Gender is required")
        @JsonProperty("gender") Gender gender,

        @JsonProperty("profile_image_url") String profileImageUrl,

        @JsonProperty("communication_preference") CommunicationPreference communicationPreference,

        @AssertTrue(message = "You must accept the terms")
        @JsonProperty("terms_accepted") boolean termsAccepted,

        @NotEmpty(message = "client is required")
        @JsonProperty("client") CreateClientCommand client,

        @NotEmpty(message = "Address is required")
        @JsonProperty("address") CreateAddressCommand address,

        @NotEmpty(message = "Phones is required")
        @JsonProperty("phone") CreatePhoneCommand phone
) {
}
