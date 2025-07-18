package com.example.skilllinkbackend.features.cleaningstaff.model;

import com.example.skilllinkbackend.features.cleaningstaff.dto.CleaningStaffRegisterDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cleaning_staff")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleaningStaff {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Enumerated(EnumType.STRING)// Almacena el enum como String en la bd
    private WorkShift shift;

    public CleaningStaff(CleaningStaffRegisterDTO cleaningDto, User user) {
        this.user = user;
        this.shift = cleaningDto.shift();
    }
}
