package com.example.skilllinkbackend.features.receptionist.model;

import com.example.skilllinkbackend.features.receptionist.dto.ReceptionistRegisterDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "receptionists")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receptionist {

    @Id
    @Column(name = "user_id") // le das un nombre especial a la columna id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") // esta columna será clave foránea y primaria
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkShift shift;

    public Receptionist(ReceptionistRegisterDTO receptionistDto, User user) {
        this.user = user;
        this.shift = receptionistDto.shift();
    }
}
