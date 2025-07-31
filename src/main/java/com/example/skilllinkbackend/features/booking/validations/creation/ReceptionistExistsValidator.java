package com.example.skilllinkbackend.features.booking.validations.creation;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.booking.dto.BookingRegisterDTO;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceptionistExistsValidator implements BookingCreationValidation {

    private final IReceptionistRepository receptionistRepository;

    @Override
    public void validate(BookingRegisterDTO bookingRegisterDTO) {
        Receptionist receptionist = receptionistRepository.findById(bookingRegisterDTO.receptionistId())
                .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));
    }
}
