package com.example.skilllinkbackend.features.booking.dto;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import com.example.skilllinkbackend.features.bookingitem.dto.BookingItemResponseDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record BookingResponseDTO(
        Long id,
        Long guestId,
        Long receptionistId,
        OffsetDateTime checkIn,
        OffsetDateTime checkOut,
        ReservationStatus status,
        OffsetDateTime createdAt,
        List<BookingItemResponseDTO> bookingItems
) {
    public BookingResponseDTO(Booking booking) {
        this(
                booking.getId(),
                booking.getGuest().getUserId(),
                booking.getReceptionist().getId(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getStatus(),
                booking.getCreatedAt(),
                booking.getBookingItems().stream().map(BookingItemResponseDTO::new).toList()
        );
    }
}
