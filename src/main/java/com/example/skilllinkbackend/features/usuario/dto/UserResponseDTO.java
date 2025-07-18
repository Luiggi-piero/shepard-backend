package com.example.skilllinkbackend.features.usuario.dto;

import com.example.skilllinkbackend.features.role.model.Role;
import com.example.skilllinkbackend.features.usuario.model.User;

import java.util.Set;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String goals,
        String email,
        String biography,
        String photo,
        boolean enabled,
        Set<Role> roles
) {
    public UserResponseDTO(User user) {
        this(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGoals(),
                user.getUsername(),
                user.getBiography(),
                user.getPhoto(),
                user.isEnabled(),
                user.getRoles()
        );
    }
}
