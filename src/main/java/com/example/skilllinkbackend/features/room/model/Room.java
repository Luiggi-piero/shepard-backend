package com.example.skilllinkbackend.features.room.model;

import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Room extends Accommodation {
    private String number;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType type; // Ej: "SIMPLE", "DOUBLE", etc.
}
