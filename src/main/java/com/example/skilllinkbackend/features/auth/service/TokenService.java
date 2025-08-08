package com.example.skilllinkbackend.features.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.skilllinkbackend.features.usuario.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.secret}")
    private String apiSecret;

    public String generateToken(User user){
        try {
            Algorithm algorithm =  Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Shepard")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getUserId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    // Obtener el nombre del usuario
    public String getSubject(String token){
        DecodedJWT verifier = null;
        if(token == null) {
            throw new RuntimeException();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Skill Link")
                    .build()
                    .verify(token);
            verifier.getSubject();
        }catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }

        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier inválido");
        }
        return verifier.getSubject();
    }
}
