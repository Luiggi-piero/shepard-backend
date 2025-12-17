package com.example.skilllinkbackend.features.auth.service.password;

import com.example.skilllinkbackend.features.auth.dto.ChangePasswordRequestDTO;
import com.example.skilllinkbackend.features.auth.dto.RenewPasswordRequestDTO;
import jakarta.mail.MessagingException;

public interface IPasswordService {
    void requestPasswordRenewal(String email) throws MessagingException;

    void renewPassword(RenewPasswordRequestDTO dto);

    void changePassword(String email, ChangePasswordRequestDTO dto);
}
