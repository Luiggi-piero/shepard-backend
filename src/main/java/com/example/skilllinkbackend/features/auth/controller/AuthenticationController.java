package com.example.skilllinkbackend.features.auth.controller;

import com.example.skilllinkbackend.features.auth.dto.UserAuthenticationDataDTO;
import com.example.skilllinkbackend.features.auth.model.DataJWTToken;
import com.example.skilllinkbackend.features.auth.service.TokenService;
import com.example.skilllinkbackend.features.auth.validation.password.IPasswordValidationService;
import com.example.skilllinkbackend.features.usuario.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Inicio de sesión de usuarios y generación de token JWT")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IPasswordValidationService passwordValidationService;

    public AuthenticationController(AuthenticationManager authenticationManager
                                    ,TokenService tokenService
                                    ,IPasswordValidationService passwordValidationService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordValidationService = passwordValidationService;
    }

    @Operation(
            summary = "Autenticar usuario",
            description = "Permite a un usuario iniciar sesión y obtener un token JWT para futuras peticiones protegidas",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserAuthenticationDataDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa, se devuelve el token JWT"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o contraseña no válida", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationDataDTO userAuthenticationDataDTO){
        passwordValidationService.validatePassword(new String(userAuthenticationDataDTO.password()));
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationDataDTO.email(), userAuthenticationDataDTO.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new DataJWTToken(JWTToken));
    }
}