package com.example.skilllinkbackend.features.booking.service;

import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.booking.dto.BookingResponseDTO;

public interface IBookingService {
    BookingResponseDTO createBooking(BookingRegisterDTO bookingRegisterDTO);
}
