package com.example.skilllinkbackend.features.department.service;

import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentResponseDTO;

public interface IDepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentRegisterDTO departmentDTO);

    void deleteDepartment(Long id);
}
