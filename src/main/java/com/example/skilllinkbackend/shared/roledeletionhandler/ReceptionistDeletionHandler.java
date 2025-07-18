package com.example.skilllinkbackend.shared.roledeletionhandler;

import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.receptionist.repository.IReceptionistRepository;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReceptionistDeletionHandler implements RoleDeletionHandler {
    private final IReceptionistRepository receptionistRepository;

    public ReceptionistDeletionHandler(IReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public void deleteIfExistsByUserId(Long id) {
        Optional<Receptionist> receptionistOpt = receptionistRepository.findById(id);
        receptionistOpt.ifPresent(receptionistRepository::delete);
    }

    @Override
    public RolesEnum supports() {
        return RolesEnum.RECEPTION;
    }
}
