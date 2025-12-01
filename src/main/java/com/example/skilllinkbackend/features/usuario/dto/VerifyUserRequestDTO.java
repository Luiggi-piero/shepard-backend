package com.example.skilllinkbackend.features.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos necesarios para verificar el registro del usuario")
public record VerifyUserRequestDTO(

        @Schema(description = "Correo del usuario", example = "alexey@gmail.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Código de verificación", example = "78945200")
        @NotBlank
        String verificationCode
) {
}
