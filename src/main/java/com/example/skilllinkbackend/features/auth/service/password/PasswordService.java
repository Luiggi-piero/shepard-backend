package com.example.skilllinkbackend.features.auth.service.password;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.auth.dto.RenewPasswordRequestDTO;
import com.example.skilllinkbackend.features.auth.validation.password.IPasswordValidationService;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.features.usuario.repository.IUserRepository;
import com.example.skilllinkbackend.shared.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService implements IPasswordService {

    private final EmailService emailService;
    private final IUserRepository userRepository;
    private final IPasswordValidationService passwordValidationService;

    @Value("${frontend.reset-password.url}")
    private String resetPasswordBaseUrl;

    // (1)
    // - Envia un correo con la url para renovar la contrasenia, el usuario ya se encuentra registrado
    // - El usuario solo recuerda su correo
    @Transactional
    @Override
    public void requestPasswordRenewal(String email) throws MessagingException {
        User user = Optional.ofNullable((User) userRepository.findByEmail(email))
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        String token = UUID.randomUUID().toString();

        user.setVerificationCode(token);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);

        // - Envia un correo con una ruta del front que contiene el token
        // - El token servira para identificar a que registro cambiar la contrasenia
        String resetLink = resetPasswordBaseUrl + "?token=" + token;

        emailService.sendNotification(user.getUsername(), "Renovar contraseña", resetLink);
    }

    // (2)
    // - Ocurreo luego del (1)
    // - Verifica el token y cambia la contrasenia
    // - Si el token expiro puedes usar nuevamente el endpoint que es usado por (1)
    @Override
    public void renewPassword(RenewPasswordRequestDTO dto) {

        passwordValidationService.validatePassword(dto.newPassword());

        User user = userRepository.findByVerificationCode(dto.token())
                .orElseThrow(() -> new NotFoundException("Token inválido"));

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("El token expiró, por favor genere otro");
        }

        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);

        user.setPassword(dto.newPassword().toCharArray());

        userRepository.save(user);
    }
}
