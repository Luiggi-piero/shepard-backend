package com.example.skilllinkbackend.features.booking.service;

import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface IBookingService {
    BookingResponseDTO createBooking(BookingRegisterDTO bookingRegisterDTO);

    void deleteBooking(Long id);

    Page<BookingResponseDTO> findAllBooking(
            Pageable pageable,
            ReservationStatus status,
            Long guestId,
            Long receptionistId,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            String guestFirstName
    );

    BookingResponseDTO findById(Long id);
}
