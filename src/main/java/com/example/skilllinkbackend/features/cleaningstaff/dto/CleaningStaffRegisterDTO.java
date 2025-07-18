package com.example.skilllinkbackend.features.cleaningstaff.dto;

import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.validation.constraints.NotNull;

public record CleaningStaffRegisterDTO(
        @NotNull
        WorkShift shift
) {
}
