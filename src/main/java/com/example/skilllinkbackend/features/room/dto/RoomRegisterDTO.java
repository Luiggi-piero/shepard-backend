package com.example.skilllinkbackend.features.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para crear una habitación")
public record RoomRegisterDTO(
        @Schema(description = "Piso de la habitación", example = "1")
        @NotNull(message = "El piso no puede ser nulo")
        @Min(value = 1, message = "El piso debe ser al menos 1")
        Integer floor,

        @Schema(description = "Precio por noche", example = "50")
        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @Schema(description = "Número o código de la habitación", example = "101")
        @NotBlank(message = "El número de habitación no puede estar vacío")
        String number,

        @Schema(description = "ID del tipo de habitación", example = "1")
        @NotNull(message = "El id del tipo de habitación no puede ser nulo")
        Long roomTypeId
) {
}
