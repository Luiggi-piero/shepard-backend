package com.example.skilllinkbackend.features.booking.validations.dto;

import java.util.List;

public record TimeValidationResult(boolean isValid, List<Long> conflictedAccommodationIds) {
}
