package com.example.skilllinkbackend.features.department.service;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentResponseDTO;
import com.example.skilllinkbackend.features.department.model.Department;
import com.example.skilllinkbackend.features.department.repository.IDepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;

    public DepartmentService(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRegisterDTO departmentDTO) {
        Department department = new Department(departmentDTO);
        return new DepartmentResponseDTO(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamento no encontrado"));
        department.deactive();
    }
}
