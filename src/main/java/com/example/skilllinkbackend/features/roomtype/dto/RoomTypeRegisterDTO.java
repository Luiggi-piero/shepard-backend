package com.example.skilllinkbackend.features.roomtype.dto;

import jakarta.validation.constraints.NotBlank;

public record RoomTypeRegisterDTO(
        @NotBlank
        String name
) {
}
