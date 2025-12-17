package com.example.skilllinkbackend.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos necesarios para cambiar la contraseña")
public record ChangePasswordRequestDTO(
        @Schema(description = "Contraseña actual", example = "123456789A!")
        @NotBlank
        String currentPassword,

        @Schema(description = "Nueva contraseña", example = "12345678910A!")
        @NotBlank
        String newPassword
) {
}
