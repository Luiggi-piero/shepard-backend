package com.example.skilllinkbackend.features.department.model;

import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Department extends Accommodation {
    private String code;
    private Integer roomsCount;

    public Department(DepartmentRegisterDTO departmentDTO) {
        this.setFloor(departmentDTO.floor());
        this.setPrice(departmentDTO.price());
        this.code = departmentDTO.code();
        this.roomsCount = departmentDTO.roomsCount();
    }
}
