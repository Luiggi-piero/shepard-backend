package com.example.skilllinkbackend.features.bookingitem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookingItemRegisterDTO(
        @Schema(example = "101", description = "ID del alojamiento")
        @NotNull(message = "El ID del alojamiento es necesario")
        Long accommodationId,

        @Schema(example = "150.00", description = "Precio del alojamiento por el tiempo de reserva")
        @NotNull(message = "El precio del alojamiento no puede ser nulo")
        @DecimalMin("0.0")
        BigDecimal price
) {
}
