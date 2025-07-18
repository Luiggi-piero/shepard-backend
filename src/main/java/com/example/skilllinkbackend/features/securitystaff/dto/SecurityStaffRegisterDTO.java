package com.example.skilllinkbackend.features.securitystaff.dto;

import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.validation.constraints.NotNull;

public record SecurityStaffRegisterDTO(
        @NotNull
        WorkShift shift
) {
}
