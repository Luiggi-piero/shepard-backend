package com.example.skilllinkbackend.features.securitystaff.model;

import com.example.skilllinkbackend.features.securitystaff.dto.SecurityStaffRegisterDTO;
import com.example.skilllinkbackend.features.usuario.model.User;
import com.example.skilllinkbackend.shared.enums.WorkShift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "security_staff")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityStaff {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkShift shift;

    public SecurityStaff(SecurityStaffRegisterDTO securityDto, User user) {
        this.user = user;
        this.shift = securityDto.shift();
    }
}
