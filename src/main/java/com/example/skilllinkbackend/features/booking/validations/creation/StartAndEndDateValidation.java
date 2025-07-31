package com.example.skilllinkbackend.features.booking.validations.creation;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class StartAndEndDateValidation implements BookingCreationValidation {
    @Override
    public void validate(BookingRegisterDTO bookingRegisterDTO) {
        OffsetDateTime checkInNew = bookingRegisterDTO.checkIn();
        OffsetDateTime checkOutNew = bookingRegisterDTO.checkOut();

        if (checkInNew.isAfter(checkOutNew))
            throw new BadRequestException("La fecha de inicio no puede ser superior a la fecha de salida");
    }
}
