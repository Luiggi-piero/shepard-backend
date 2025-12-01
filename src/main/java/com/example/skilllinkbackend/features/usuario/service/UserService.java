package com.example.skilllinkbackend.features.usuario.service;

import com.example.skilllinkbackend.config.exceptions.BadRequestException;
import com.example.skilllinkbackend.config.exceptions.DuplicateResourceException;
import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.auth.service.TokenService;
import com.example.skilllinkbackend.features.auth.validation.password.IPasswordValidationService;
import com.example.skilllinkbackend.features.role.model.Role;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import com.example.skilllinkbackend.features.role.repository.IRoleRepository;
import com.example.skilllinkbackend.features.usuario.dto.*;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.features.usuario.repository.IUserRepository;
import com.example.skilllinkbackend.shared.roledeletionhandler.RoleDeletionHandler;
import com.example.skilllinkbackend.shared.rolegistrationhandler.RoleRegistrationHandler;
import com.example.skilllinkbackend.shared.service.EmailService;
import com.example.skilllinkbackend.shared.util.RoleUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IPasswordValidationService passwordValidationService;
    private final IRoleRepository roleRepository;
    private final TokenService tokenService;
    private final List<RoleRegistrationHandler> roleHandlers;
    private final RoleUtil roleUtil;
    private final List<RoleDeletionHandler> deletionHandlers;
    private final EmailService emailService;

    public UserService(IUserRepository userRepository,
                       IPasswordValidationService passwordValidationService,
                       IRoleRepository roleRepository,
                       TokenService tokenService,
                       List<RoleRegistrationHandler> roleHandlers,
                       RoleUtil roleUtil,
                       List<RoleDeletionHandler> deletionHandlers,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordValidationService = passwordValidationService;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.roleHandlers = roleHandlers;
        this.roleUtil = roleUtil;
        this.deletionHandlers = deletionHandlers;
        this.emailService = emailService;
    }

    @Override
    public Page<UserResponseDTO> getUsers(Pageable pagination) {
        return userRepository.findAll(pagination).map(UserResponseDTO::new);
    }

    @Transactional
    @Override
    public RegisteredUserResponseDTO createUser(UserRegisterRequestDTO userDto) throws MessagingException{
        validateUserUniqueness(userDto);
        passwordValidationService.validatePassword(new String(userDto.password()));
        User user = new User(userDto);
        user.setRoles(getRolesFromEnum(userDto.roles()));
        assignDefaultRole(user);

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false); //para activacion por mail.

        sendVerificationByEmail(user);

        User userResponse = userRepository.save(user);
        String token = tokenService.generateToken(userResponse);

        // Delegar manejo de roles a sus handlers
        // Si el rol es MENTOR -> crea un mentor, usa MentorRegistrationHandler
        // Si el rol es MENTEE -> crea un mentee, usa MenteeRegistrationHandler
        /*
        * Para cada rol que tiene el usuario, busca si hay una clase que sepa manejar ese rol (un handler),
        * y si la hay, ejecuta su lógica con el usuario y el DTO.
        * */
        for (RolesEnum role : userDto.roles()){
            roleHandlers.stream()
                    .filter(handler -> handler.supports() == role)
                    .findFirst()
                    .ifPresent(handler -> handler.handle(userResponse, userDto));
        }

        return new RegisteredUserResponseDTO(userResponse, token);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        user.deactive();
    }

    @Override
    public UserResponseDTO findByUserId(Long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userDto) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        user.update(userDto, roleUtil.getRolesFromEnum(userDto.roles()));

        // Borrar el registro de ese usuario(si existiera) de las tablas de roles
        deleteRolesForUser(id, Set.of(RolesEnum.CLEANING, RolesEnum.RECEPTION, RolesEnum.SECURITY));

        // Registrar el usuario en las tablas de roles asignados(receptionists, security_staff...)
        for (RolesEnum role : userDto.roles()){
            roleHandlers.stream()
                    .filter(handler -> handler.supports() == role)
                    .findFirst()
                    .ifPresent(handler -> handler.handle(user, new UserRegisterRequestDTO(userDto)));
        }

        return new UserResponseDTO(user);
    }

    @Override
    public void verifyUser(VerifyUserRequestDTO input) {
        User user = userRepository.findByEmailVerification(input.email())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (user.isEnabled()){
            throw new BadRequestException("La cuenta ya se encuentra verificada " + user.getUsername());
        }
        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException(("El código de verificación ya expiró"));
        }
        if (!user.getVerificationCode().equals(input.verificationCode())){
            throw new BadRequestException("El código de verificación es inválido");
        }
        // Todo bien
        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);

        userRepository.save(user);
    }

    /**
     * Elimina el registro(por userId) de las tablas de roles(mentors, mentee...) si existe
     * @param userId
     * @param rolesToDelete
     */
    private void deleteRolesForUser(Long userId, Set<RolesEnum> rolesToDelete) {
        for (RolesEnum role: rolesToDelete){
            deletionHandlers.stream()
                    .filter(handler -> handler.supports() == role)
                    .findFirst()
                    .ifPresent(handler -> handler.deleteIfExistsByUserId(userId));
        }
    }

    private void validateUserUniqueness(UserRegisterRequestDTO userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new DuplicateResourceException("El correo electrónico ya existe");
        }
    }

    private void assignDefaultRole(User user) {
        Role role = roleRepository.findByName(RolesEnum.USER)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
        user.getRoles().add(role);
    }

    private Set<Role> getRolesFromEnum(Set<RolesEnum> rolesEnums) {
        return rolesEnums.stream()
                .map(
                        roleName -> roleRepository.findByName(roleName)
                                .orElseThrow(() ->
                                        new NotFoundException("Rol no encontrado, valores posibles USER, ADMIN, GUEST, CLEANING, SECURITY, RECEPTION, valor entregado: " + roleName))
                ).collect(Collectors.toSet());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    private void sendVerificationByEmail(User user) throws MessagingException {
        String subject = "Verificacion de cuenta";
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Bienvenido a tu app!</h2>"
                + "<p style=\"font-size: 16px;\">Ingrese el código de verificación a continuación para continuar:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Codigo de verificacion:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + user.getVerificationCode() + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        emailService.sendNotification(user.getUsername(), subject, htmlMessage);
    }
}
