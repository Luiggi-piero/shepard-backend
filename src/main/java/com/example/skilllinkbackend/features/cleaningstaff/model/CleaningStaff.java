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
    @Column(name = "user_id") // le das un nombre especial a la columna id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") // esta columna será clave foránea y primaria
    private User user;

    @Enumerated(EnumType.STRING)// Almacena el enum como String en la bd
    private WorkShift shift;

    public CleaningStaff(CleaningStaffRegisterDTO cleaningDto, User user) {
        this.user = user;
        this.shift = cleaningDto.shift();
    }
}
