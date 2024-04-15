package com.claudionetto.gamelibrary.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Name can't be null or empty")
        String name,
        @Size(min = 6, max = 24, message = "Username must be between 6 and 24 in length")
        @NotBlank
        String username,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8}$",
                message = "Password must be min 8 length containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
        String password,
        @Email
        @NotBlank
        String email
) {
}
