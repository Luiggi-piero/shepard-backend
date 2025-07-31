package com.example.skilllinkbackend.features.bookingitem.dto;

import com.example.skilllinkbackend.features.bookingitem.model.BookingItem;

import java.math.BigDecimal;

public record BookingItemResponseDTO(
        Long id,
        Long bookingId,
        Long accommodationId,
        BigDecimal price
) {
    public BookingItemResponseDTO(BookingItem bookingItem) {
        this(
                bookingItem.getId(),
                bookingItem.getBooking().getId(),
                bookingItem.getAccommodation().getId(),
                bookingItem.getPrice()
        );
    }
}
