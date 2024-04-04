package com.claudionetto.gamelibrary.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "The name can't be null or empty")
        String name,
        @Size(min = 6, max = 24, message = "Username needs to has the size between 6 and 24")
        @NotBlank
        String username,
        String password,
        @Email
        @NotBlank
        String email
) {
}
