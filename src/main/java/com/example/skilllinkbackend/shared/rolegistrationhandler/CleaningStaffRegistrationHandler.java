package com.example.skilllinkbackend.shared.rolegistrationhandler;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.features.cleaningstaff.dto.CleaningStaffRegisterDTO;
import com.example.skilllinkbackend.features.cleaningstaff.model.CleaningStaff;
import com.example.skilllinkbackend.features.cleaningstaff.repository.ICleaningStaffRepository;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import org.springframework.stereotype.Component;

@Component
public class CleaningStaffRegistrationHandler implements RoleRegistrationHandler {

    private final ICleaningStaffRepository cleaningStaffRepository;

    public CleaningStaffRegistrationHandler(ICleaningStaffRepository cleaningStaffRepository) {
        this.cleaningStaffRepository = cleaningStaffRepository;
    }

    @Override
    public void handle(User user, UserRegisterRequestDTO dto) {
        CleaningStaffRegisterDTO cleaningDTO = dto.cleaningStaff();
        if (cleaningDTO == null)
            throw new BadRequestException(("La propiedad cleaningStaff es necesaria para el rol CLEANING"));

        CleaningStaff cleaningStaff = new CleaningStaff(cleaningDTO, user);
        cleaningStaffRepository.save(cleaningStaff);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.CLEANING;
    }
}
