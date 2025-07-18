package com.example.skilllinkbackend.shared.roledeletionhandler;

import com.example.skilllinkbackend.features.cleaningstaff.model.CleaningStaff;
import com.example.skilllinkbackend.features.cleaningstaff.repository.ICleaningStaffRepository;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CleaningStaffDeletionHandler implements RoleDeletionHandler {

    private final ICleaningStaffRepository cleaningStaffRepository;

    public CleaningStaffDeletionHandler(ICleaningStaffRepository cleaningStaffRepository) {
        this.cleaningStaffRepository = cleaningStaffRepository;
    }

    @Override
    public void deleteIfExistsByUserId(Long id) {
        Optional<CleaningStaff> cleaningStaffOpt = cleaningStaffRepository.findById(id);
        cleaningStaffOpt.ifPresent(cleaningStaffRepository::delete);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.CLEANING;
    }
}
