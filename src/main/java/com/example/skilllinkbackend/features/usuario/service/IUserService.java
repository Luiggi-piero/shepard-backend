package com.example.skilllinkbackend.features.usuario.service;

import com.example.skilllinkbackend.features.usuario.dto.RegisteredUserResponseDTO;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.dto.UserResponseDTO;
import com.example.skilllinkbackend.features.usuario.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserResponseDTO> getUsers(Pageable pagination);

    RegisteredUserResponseDTO createUser(UserRegisterRequestDTO userDto);

    void deleteUser(Long id);

    UserResponseDTO findByUserId(Long id);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userDto);
}

