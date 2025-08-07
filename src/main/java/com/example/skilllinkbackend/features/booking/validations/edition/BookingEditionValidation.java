package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;

public interface BookingEditionValidation {
    void validate(BookingUpdateDTO bookingUpdateDTO);
}
