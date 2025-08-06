package com.example.skilllinkbackend.features.booking.repository;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface BookingRepositoryCustom {
    Page<Booking> findAllBookingWithFilters(
            Pageable pageable,
            ReservationStatus status,
            Long guestId,
            Long receptionistId,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            String guestFirstName
    );
}
