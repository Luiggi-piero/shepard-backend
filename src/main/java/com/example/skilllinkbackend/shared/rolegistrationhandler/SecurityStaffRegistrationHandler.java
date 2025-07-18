package com.example.skilllinkbackend.shared.rolegistrationhandler;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.securitystaff.dto.SecurityStaffRegisterDTO;
import com.example.skilllinkbackend.features.securitystaff.model.SecurityStaff;
import com.example.skilllinkbackend.features.securitystaff.repository.ISecurityStaffRepository;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import org.springframework.stereotype.Component;

@Component
public class SecurityStaffRegistrationHandler implements RoleRegistrationHandler {
    private final ISecurityStaffRepository securityStaffRepository;

    public SecurityStaffRegistrationHandler(ISecurityStaffRepository securityStaffRepository) {
        this.securityStaffRepository = securityStaffRepository;
    }

    @Override
    public void handle(User user, UserRegisterRequestDTO dto) {
        SecurityStaffRegisterDTO securityDto = dto.securityStaff();
        if (securityDto == null)
            throw new BadRequestException("La propiedad securityStaff es necesaria para el rol SECURITY");

        SecurityStaff securityStaff = new SecurityStaff(securityDto, user);
        securityStaffRepository.save(securityStaff);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.SECURITY;
    }
}
