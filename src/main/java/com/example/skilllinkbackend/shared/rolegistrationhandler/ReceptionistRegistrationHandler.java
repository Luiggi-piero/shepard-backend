package com.example.skilllinkbackend.shared.rolegistrationhandler;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.features.receptionist.dto.ReceptionistRegisterDTO;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import org.springframework.stereotype.Component;

@Component
public class ReceptionistRegistrationHandler implements RoleRegistrationHandler {

    private final IReceptionistRepository receptionistRepository;

    public ReceptionistRegistrationHandler(IReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public void handle(User user, UserRegisterRequestDTO dto) {
        ReceptionistRegisterDTO receptionDto = dto.receptionist();
        if (receptionDto == null)
            throw new BadRequestException("La propiedad receptionist es necesaria para el rol RECEPTION");

        Receptionist receptionist = new Receptionist(receptionDto, user);
        receptionistRepository.save(receptionist);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.RECEPTION;
    }
}
