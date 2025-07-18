package com.example.skilllinkbackend.shared.roledeletionhandler;

import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.securitystaff.model.SecurityStaff;
import com.example.skilllinkbackend.features.securitystaff.repository.ISecurityStaffRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityStaffDeletionHandler implements RoleDeletionHandler {

    private final ISecurityStaffRepository securityStaffRepository;

    public SecurityStaffDeletionHandler(ISecurityStaffRepository securityStaffRepository) {
        this.securityStaffRepository = securityStaffRepository;
    }

    @Override
    public void deleteIfExistsByUserId(Long id) {
        Optional<SecurityStaff> securityStaffOpt = securityStaffRepository.findById(id);
        securityStaffOpt.ifPresent(securityStaffRepository::delete);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.SECURITY;
    }
}
