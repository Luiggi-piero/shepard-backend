package com.example.skilllinkbackend.features.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos necesarios para iniciar sesión")
public record UserAuthenticationDataDTO(
        @Schema(description = "Correo del usuario", example = "alexey@gmail.com")
        @NotBlank
        String email,

        @Schema(description = "Contraseña del usuario", example = "12345678A")
        @NotBlank
        String password
) {
}
