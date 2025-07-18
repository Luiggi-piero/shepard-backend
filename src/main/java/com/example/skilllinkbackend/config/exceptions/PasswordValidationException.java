package com.example.skilllinkbackend.config.exceptions;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException(String message) {
        super(message);
    }
}
