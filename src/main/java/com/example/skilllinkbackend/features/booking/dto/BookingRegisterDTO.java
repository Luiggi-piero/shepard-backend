package com.example.skilllinkbackend.features.booking.dto;

import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemRegisterDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;

public record BookingRegisterDTO(

        @NotNull(message = "El ID del huésped no puede ser nulo")
        Long guestId,

        @NotNull(message = "El ID del recepcionista no puede ser nulo")
        Long receptionistId,

        @NotNull(message = "La fecha de inicio no puede ser nulo")
        OffsetDateTime checkIn,

        @NotNull(message = "La fecha de salida no puede ser nulo")
        OffsetDateTime checkOut,

        @NotNull(message = "La lista de items es requerido")
        @Size(min = 1, message = "Se requiere al menos un elemento de reserva")
        @Valid
        List<BookingItemRegisterDTO> bookingItems
) {
}
