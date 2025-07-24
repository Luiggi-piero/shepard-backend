package com.example.skilllinkbackend.features.department.service;

import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentResponseDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentRegisterDTO departmentDTO);

    void deleteDepartment(Long id);

    Page<DepartmentResponseDTO> findAllDepartment(Pageable pagination);

    DepartmentResponseDTO getDepartmentById(Long id);

    DepartmentResponseDTO updateDepartment(Long id, DepartmentUpdateDTO departmentDTO);
}
