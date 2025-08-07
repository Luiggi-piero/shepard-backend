package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.booking.repository.IBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EditBookingExistsValidator implements BookingEditionValidation {
    private final IBookingRepository bookingRepository;

    @Override
    public void validate(BookingUpdateDTO bookingUpdateDTO) {
        bookingRepository.findByIdAndEnabledTrue(bookingUpdateDTO.id())
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    }
}
