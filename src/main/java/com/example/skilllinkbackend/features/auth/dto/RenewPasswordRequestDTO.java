package com.example.skilllinkbackend.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos necesarios para renovar la contraseña")
public record RenewPasswordRequestDTO(
        @Schema(description = "Token recibido", example = "123459A-123459A-123459A")
        @NotBlank
        String token,

        @Schema(description = "Nueva contraseña", example = "123456789A!")
        @NotBlank
        String newPassword
) {
}
