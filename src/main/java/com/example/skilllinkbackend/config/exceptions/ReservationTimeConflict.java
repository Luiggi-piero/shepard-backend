package com.example.skilllinkbackend.config.exceptions;

import com.example.skilllinkbackend.features.booking.validations.dto.TimeValidationResult;


public class ReservationTimeConflict extends RuntimeException {
    TimeValidationResult timeValidationResult;

    public ReservationTimeConflict(String message, TimeValidationResult timeValidationResult) {
        super(message);
        this.timeValidationResult = timeValidationResult;
    }
}
