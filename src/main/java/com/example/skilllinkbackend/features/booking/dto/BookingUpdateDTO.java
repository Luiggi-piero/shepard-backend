package com.example.skilllinkbackend.features.booking.dto;

import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Datos necesarios para editar una reserva")
public record BookingUpdateDTO(

        @Schema(description = "ID de la reserva", example = "1")
        @NotNull(message = "El ID de la reserva no puede ser nulo")
        Long id,

        @Schema(description = "ID del huésped", example = "1")
        @NotNull(message = "El ID del huésped no puede ser nulo")
        Long guestId,

        @Schema(description = "ID del recepcionista", example = "1")
        @NotNull(message = "El ID del recepcionista no puede ser nulo")
        Long receptionistId,

        @Schema(description = "Fecha de inicio de la reserva", example = "2025-07-28T11:00:00Z")
        @NotNull(message = "La fecha de inicio no puede ser nulo")
        OffsetDateTime checkIn,

        @Schema(description = "Fecha final de la reserva", example = "2025-08-13T11:00:00-05:00")
        @NotNull(message = "La fecha de salida no puede ser nulo")
        OffsetDateTime checkOut,

        @Schema(description = "Estado de la reserva", example = "RESERVED")
        @NotNull(message = "El estado de la reserva no puede ser nulo")
        ReservationStatus status,

        @Schema(description = "Lista de items de la reserva",
                example = "[{\"accommodationId\": 101, \"price\": 150.00}]")
        @NotNull(message = "La lista de items es requerido")
        @Size(min = 1, message = "Se requiere al menos un elemento de reserva")
        @Valid
        List<BookingItemUpdateDTO> bookingItems
) {
}
