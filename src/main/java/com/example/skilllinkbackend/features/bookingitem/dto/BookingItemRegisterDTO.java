package com.example.skilllinkbackend.features.bookingitem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookingItemRegisterDTO(
        @NotNull(message = "El ID del alojamiento es necesario")
        Long accommodationId,

        @NotNull(message = "El precio del alojamiento no puede ser nulo")
        @DecimalMin("0.0")
        BigDecimal price
) {
}
