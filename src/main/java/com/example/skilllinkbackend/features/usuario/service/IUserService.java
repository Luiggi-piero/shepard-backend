package com.example.skilllinkbackend.features.usuario.service;

import com.example.skilllinkbackend.features.usuario.dto.*;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserResponseDTO> getUsers(Pageable pagination);

    RegisteredUserResponseDTO createUser(UserRegisterRequestDTO userDto) throws MessagingException;

    void deleteUser(Long id);

    UserResponseDTO findByUserId(Long id);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userDto);

    // verifica si el codigo de verificacion es el correcto
    void verifyUser(VerifyUserRequestDTO input);
}

