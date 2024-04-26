package com.claudionetto.gamelibrary.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO (
        @NotBlank String username,
        @NotBlank String password
){
}
