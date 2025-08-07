package com.example.skilllinkbackend.config.exceptions;

import com.example.skilllinkbackend.features.booking.validations.dto.TimeValidationResultResponseDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<Object> handlePasswordValidationException(PasswordValidationException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        var errors = ex.getFieldErrors().stream().map(DataErrorValidation::new).toList();
        return buildResponseEntity(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<Object> handleInvalidRoleException(InvalidRoleException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(ReservationTimeConflict.class)
    public ResponseEntity<Object> handleReservationTimeConflict(ReservationTimeConflict ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                new TimeValidationResultResponseDTO(ex.getMessage(),
                        ex.timeValidationResult.conflictedAccommodationIds()),
                request);
    }

    // Manejo de la excepcion del formato de fechas y enums para el @RequestBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            // Verifica si el error es por un Enum
            Class<?> targetType = invalidFormatException.getTargetType();
            if (targetType.isEnum()) {
                Map<String, Object> error = new HashMap<>();
                error.put("message", "Valor inválido para el campo tipo enum: " + targetType.getSimpleName());
                error.put("valueReceived", invalidFormatException.getValue());
                error.put("acceptedValues", targetType.getEnumConstants());
                return buildResponseEntity(HttpStatus.BAD_REQUEST, error, request);
            }
        }

        String message = "Error de formato en los datos enviados. Verifica que las fechas estén en formato ISO 8601, por ejemplo: '2025-08-10T14:00:00-05:00' o '2025-09-01T14:00:00Z'.";
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message, request);
    }

    // Manejo de la excepcion del formato de fechas para el @RequesParam
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleDateFormatError(MethodArgumentTypeMismatchException ex, WebRequest request) {
        Map<String, String> error = new HashMap<>();

        if (ex.getRequiredType() == OffsetDateTime.class) {
            error.put("general message", "Formato de fecha inválido");
            error.put("parameter", ex.getName());
            error.put("detail", "Usa el formato ISO 8601, por ejemplo: 2025-08-06T11:10:00Z o 2025-08-13T11:00:00-05:00");
        } else {
            error.put("general message", "Tipo de dato inválido");
            error.put("parameter", ex.getName());
            error.put("detail", "Se esperaba: " + ex.getRequiredType().getSimpleName());
        }

        return buildResponseEntity(HttpStatus.BAD_REQUEST, error, request);
    }

    private <T> ResponseEntity<Object> buildResponseEntity(HttpStatus status, T content, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", content);
        body.put("path", extractPath(request));
        return new ResponseEntity<>(body, status);
    }

    private String extractPath(WebRequest request) {
        return request.getDescription(false).substring(4); // Remove 'uri=' prefix
    }

    private record DataErrorValidation(String field, String error) {
        public DataErrorValidation(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}