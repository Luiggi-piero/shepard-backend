package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EditReceptionistExistsValidator implements BookingEditionValidation {

    private final IReceptionistRepository receptionistRepository;

    @Override
    public void validate(BookingUpdateDTO bookingUpdateDTO) {
        Receptionist receptionist = receptionistRepository.findById(bookingUpdateDTO.receptionistId())
                .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));
    }
}
