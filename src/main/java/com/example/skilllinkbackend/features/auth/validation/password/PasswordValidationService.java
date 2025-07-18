package com.example.skilllinkbackend.features.auth.validation.password;

import com.example.skilllinkbackend.config.exceptions.PasswordValidationException;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidationService implements IPasswordValidationService {

    @Override
    public void validatePassword(String password) {
        if (password.length() < 8 || password.length() > 15
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*\\d.*")) {
            throw new PasswordValidationException("La contraseña debe tener entre 8 y 15 caracteres y contener al menos una letra mayúscula y un número");
        }
    }
}
