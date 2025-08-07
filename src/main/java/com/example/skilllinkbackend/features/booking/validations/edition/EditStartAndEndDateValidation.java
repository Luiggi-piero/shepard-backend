package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class EditStartAndEndDateValidation implements BookingEditionValidation {
    @Override
    public void validate(BookingUpdateDTO bookingUpdateDTO) {
        OffsetDateTime checkInNew = bookingUpdateDTO.checkIn();
        OffsetDateTime checkOutNew = bookingUpdateDTO.checkOut();

        if (checkInNew.isAfter(checkOutNew))
            throw new BadRequestException("La fecha de inicio no puede ser superior a la fecha de salida");

    }
}
