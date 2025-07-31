package com.example.skilllinkbackend.features.booking.validations.dto;

import java.util.List;

public record TimeValidationResultResponseDTO(
        String detail,
        List<Long> conflictedAccommodationIds
) {
}
