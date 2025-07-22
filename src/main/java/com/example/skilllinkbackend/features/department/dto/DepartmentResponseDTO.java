package com.example.skilllinkbackend.features.department.dto;

import com.example.skilllinkbackend.features.department.model.Department;

import java.math.BigDecimal;

public record DepartmentResponseDTO(
        Long id,
        Integer floor,
        BigDecimal price,
        boolean available,
        boolean enabled,
        String code,
        Integer roomsCount
) {
    public DepartmentResponseDTO(Department department) {
        this(
                department.getId(),
                department.getFloor(),
                department.getPrice(),
                department.isAvailable(),
                department.isEnabled(),
                department.getCode(),
                department.getRoomsCount()
        );
    }
}
