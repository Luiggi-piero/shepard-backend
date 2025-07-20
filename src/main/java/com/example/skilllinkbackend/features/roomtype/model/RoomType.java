package com.example.skilllinkbackend.features.roomtype.model;

import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_types")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean enabled = true;

    public RoomType(RoomTypeRegisterDTO roomTypeDto) {
        this.name = roomTypeDto.name();
    }
}
