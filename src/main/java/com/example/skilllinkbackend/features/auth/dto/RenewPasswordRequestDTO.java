package com.example.skilllinkbackend.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RenewPasswordRequestDTO(
        @NotBlank
        String token,

        @NotBlank
        String newPassword
) {
}
