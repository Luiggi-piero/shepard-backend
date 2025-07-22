package com.example.skilllinkbackend.features.department.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentResponseDTO;
import com.example.skilllinkbackend.features.department.service.IDepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/departments")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataResponse<DepartmentResponseDTO>> createDepartment(
            @RequestBody @Valid DepartmentRegisterDTO departmentRegisterDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.createDepartment(departmentRegisterDTO);
        URI url = uriComponentsBuilder.path("/departments/{id}").buildAndExpand(departmentResponseDTO.id()).toUri();
        DataResponse<DepartmentResponseDTO> response = new DataResponse<>(
                "Departamento creado con éxito",
                HttpStatus.CREATED.value(),
                departmentResponseDTO
        );
        return ResponseEntity.created(url).body(response);
    }
}
