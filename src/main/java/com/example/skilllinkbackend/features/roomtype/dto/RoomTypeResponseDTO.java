package com.example.skilllinkbackend.features.roomtype.dto;

import com.example.skilllinkbackend.features.roomtype.model.RoomType;

public record RoomTypeResponseDTO(
        Long id,
        String name,
        boolean enabled
) {
    public RoomTypeResponseDTO(RoomType roomType) {
        this(
                roomType.getId(),
                roomType.getName(),
                roomType.isEnabled()
        );
    }
}
