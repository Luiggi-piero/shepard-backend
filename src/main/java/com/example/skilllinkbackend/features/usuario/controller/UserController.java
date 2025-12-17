package com.example.skilllinkbackend.features.usuario.controller;

import com.example.skilllinkbackend.config.responses.ApiResponseSimple;
import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.auth.dto.ChangePasswordRequestDTO;
import com.example.skilllinkbackend.features.auth.dto.RenewPasswordRequestDTO;
import com.example.skilllinkbackend.features.auth.service.password.IPasswordService;
import com.example.skilllinkbackend.features.usuario.dto.*;
import com.example.skilllinkbackend.features.usuario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios del sistema")
public class UserController {

    private final UserService userService;
    private final IPasswordService passwordService;

    public UserController(UserService userService, IPasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @Operation(
            summary = "Obtener lista paginada de usuarios",
            description = "Solo accesible por usuarios con rol ADMIN",
            security = @SecurityRequirement(name = "bearer-key"),
            parameters = {
                    @Parameter(name = "page", description = "Número de página (0-indexado)", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo de ordenamiento, ej: userId,asc", example = "userId,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado (no tiene el rol ADMIN)", content = @Content)
            }
    )
    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUsers(@PageableDefault(size = 10, sort = "userId") Pageable pagination) {
        Page<UserResponseDTO> usersPage = userService.getUsers(pagination);
        Map<String, Object> response = new HashMap<>();
        response.put("content", usersPage.getContent());
        response.put("currentPage", usersPage.getNumber());
        response.put("totalItems", usersPage.getTotalElements());  // total de elementos en la bd
        response.put("totalPages", usersPage.getTotalPages());
        response.put("pageSize", usersPage.getSize());
        response.put("hasNext", usersPage.hasNext());
        response.put("hasPrevious", usersPage.hasPrevious());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Registro público de un nuevo usuario en la plataforma",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o error en validación", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflicto en la DB", content = @Content)
            }
    )
    @PostMapping("register")
    public ResponseEntity<DataResponse<RegisteredUserResponseDTO>> createUser(
            @RequestBody @Valid UserRegisterRequestDTO userDto) throws MessagingException {
        RegisteredUserResponseDTO registeredUserResponseDTO = userService.createUser(userDto);
        //ApiResponse response = new ApiResponse("Usuario registrado existosamente", HttpStatus.CREATED.value());
        DataResponse<RegisteredUserResponseDTO> response = new DataResponse(
                "Usuario registrado, por favor verifique su registro, revise su correo",
                HttpStatus.CREATED.value(),
                registeredUserResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Verificar registro",
            description = "Verifica tu registro usando el código enviado por correo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario verificado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o error en validación", content = @Content)
            }
    )
    @PostMapping("register/verify-user")
    public ResponseEntity<String> verifyUserRegister(@RequestBody VerifyUserRequestDTO verifyUserRequestDTO) {
        userService.verifyUser(verifyUserRequestDTO);
        return ResponseEntity.ok("Cuenta verificada con éxito");
    }

    @Operation(
            summary = "Reenviar el código de verificación",
            description = "Reenvía el código de verificación a un correo, el usuario todavía no verifica su registro",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Código enviado"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping("resend")
    public ResponseEntity<ApiResponseSimple> resendVerificationCode(@RequestParam String email) throws MessagingException {
        userService.resendVerificationCode(email);
        return ResponseEntity.ok(new ApiResponseSimple("Código de verificación reenviado", HttpStatus.OK.value()));
    }

    @Operation(
            summary = "Eliminar un usuario por ID",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            }
    )
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener un usuario por su ID",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            }
    )
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDTO findByUserId(@PathVariable Long id) {
        return userService.findByUserId(id);
    }

    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            }
    )
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateDTO userDto) {
        return userService.updateUser(id, userDto);
    }

    @Operation(
            summary = "Solicitar la renovación de contraseña, solo con correo",
            description = "El usuario solo recuerda su correo y necesita generar otra contraseña, el usuario previamente ya se encuentra registrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Correo enviado con la url para renovar la contraseña"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            }
    )
    @PostMapping("request-password-renewal")
    public ResponseEntity<ApiResponseSimple> requestPasswordRenewal(@RequestParam String email) throws MessagingException {
        passwordService.requestPasswordRenewal(email);
        return ResponseEntity.ok(new ApiResponseSimple("Correo enviado", HttpStatus.OK.value()));
    }

    @Operation(
            summary = "Renovar contraseña",
            description = "El cliente indica el token y la nueva contraseña para su renovación, el usuario previamente ya se encuentra registrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Constraseña renovada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            }
    )
    @PostMapping("renew-password")
    public ResponseEntity<ApiResponseSimple> renewPassword(@RequestBody @Valid RenewPasswordRequestDTO dto) {
        passwordService.renewPassword(dto);
        return ResponseEntity.ok(new ApiResponseSimple("Contraseña renovada con éxito", HttpStatus.OK.value()));
    }

    @Operation(
            summary = "Cambiar contraseña",
            description = "Ocurre el cambio de contraseña, el usuario se encuentra logueado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Constraseña cambiada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválias", content = @Content)
            }
    )
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("change-password")
    public ResponseEntity<ApiResponseSimple> changePassword(
            @RequestBody @Valid ChangePasswordRequestDTO dto,
            Principal principal
    ) {
        passwordService.changePassword(principal.getName(), dto);
        return ResponseEntity.ok(new ApiResponseSimple("Contraseña cambiada con éxito", HttpStatus.OK.value()));
    }
}
