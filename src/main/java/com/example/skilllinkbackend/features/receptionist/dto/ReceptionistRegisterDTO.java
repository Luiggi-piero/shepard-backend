package com.example.skilllinkbackend.features.receptionist.dto;

import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.validation.constraints.NotNull;

public record ReceptionistRegisterDTO(
        @NotNull
        WorkShift shift
) {
}
