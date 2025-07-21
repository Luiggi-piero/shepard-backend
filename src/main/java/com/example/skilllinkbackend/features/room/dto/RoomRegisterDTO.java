package com.example.skilllinkbackend.features.room.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RoomRegisterDTO(
        @NotNull(message = "El piso no puede ser nulo")
        @Min(value = 1, message = "El piso debe ser al menos 1")
        Integer floor,

        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @NotBlank(message = "El número de habitación no puede estar vacío")
        String number,

        @NotNull(message = "El id del tipo de habitación no puede ser nulo")
        Long roomTypeId
) {
}
