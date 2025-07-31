package com.example.skilllinkbackend.features.booking.validations.creation;

import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;

public interface BookingCreationValidation {
    void validate(BookingRegisterDTO bookingRegisterDTO);
}
