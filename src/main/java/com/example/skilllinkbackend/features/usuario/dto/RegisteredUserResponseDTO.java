package com.example.skilllinkbackend.features.usuario.dto;

import com.example.skilllinkbackend.features.usuario.model.User;

public record RegisteredUserResponseDTO(
        UserResponseDTO userResponseDTO,
        String token
) {
    public RegisteredUserResponseDTO(User user, String token) {
        this(
                new UserResponseDTO(user),
                token
        );
    }
}
