package com.example.skilllinkbackend.shared.rolegistrationhandler;

import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.model.User;

// Esta interface ayuda a abstraer la creación un registro
// * Este registro puede ser un MENTOR, MENTEE, etc
// * De esta forma no crece el metodo createUser de UserService con varios if por rol
public interface RoleRegistrationHandler {
    void handle(User user, UserRegisterRequestDTO dto);

    RolesEnum supports(); // para saber a qué rol aplica
}
