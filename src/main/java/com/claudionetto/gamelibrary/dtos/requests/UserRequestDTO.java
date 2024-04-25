package com.claudionetto.gamelibrary.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank
        String name,
        @Size(min = 6, max = 24)
        @NotBlank
        String username,

        String password,
        @Email
        @NotBlank
        String email
) {
}
