package com.example.skilllinkbackend.features.room.dto;

import com.example.skilllinkbackend.features.room.model.Room;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;

import java.math.BigDecimal;

public record RoomResponseDTO(
        Long id,
        Integer floor,
        BigDecimal price,
        boolean available,
        boolean enabled,
        String number,
        RoomTypeResponseDTO roomType
) {
    public RoomResponseDTO(Room room) {
        this(
                room.getId(),
                room.getFloor(),
                room.getPrice(),
                room.isAvailable(),
                room.isEnabled(),
                room.getNumber(),
                new RoomTypeResponseDTO(room.getType())
        );
    }
}
