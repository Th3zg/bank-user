package com.user_services.user_services.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_services.user_services.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreatePhoneCommand(
        @NotNull(message = "Person ID is required")
        @JsonProperty("person_id") Long personId,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?(\\d{1,3})?[-.\\s]?(\\(?\\d{1,4}?\\)?)[-.\\s]?(\\d{1,4})[-.\\s]?(\\d{1,4})[-.\\s]?(\\d{1,9})$",
                message = "Phone number format is invalid")
        @JsonProperty("phone_number") String phoneNumber,

        @NotNull(message = "Phone type is required")
        @JsonProperty("phone_type") PhoneType phoneType
) {
}
