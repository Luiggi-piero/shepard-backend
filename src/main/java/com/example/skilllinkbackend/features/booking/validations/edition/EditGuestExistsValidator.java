package com.example.skilllinkbackend.features.booking.validations.edition;

import com.example.skilllinkbackend.config.exceptions.InvalidRoleException;
import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.booking.dto.BookingUpdateDTO;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.features.usuario.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EditGuestExistsValidator implements BookingEditionValidation {
    private final IUserRepository userRepository;

    @Override
    public void validate(BookingUpdateDTO bookingUpdateDTO) {
        // Validar que el guest exista y tenga el rol GUEST
        User guest = userRepository.findByUserId(bookingUpdateDTO.guestId())
                .orElseThrow(() -> new NotFoundException("El huésped no fue encontrado"));

        boolean hasGuestRole = guest.getRoles().stream()
                .anyMatch(role -> role.getName() == RolesEnum.GUEST);

        if (!hasGuestRole) {
            throw new InvalidRoleException("El usuario relacionado a guestId no tiene el rol GUEST");
        }
    }
}
