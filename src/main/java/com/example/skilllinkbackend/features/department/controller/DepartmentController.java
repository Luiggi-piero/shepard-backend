package com.example.skilllinkbackend.features.department.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.department.dto.DepartmentRegisterDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentResponseDTO;
import com.example.skilllinkbackend.features.department.dto.DepartmentUpdateDTO;
import com.example.skilllinkbackend.features.department.service.IDepartmentService;
import com.example.skilllinkbackend.shared.util.PaginationResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/departments")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Departamentos", description = "Operaciones relacionadas con la gestión de departamentos")
public class DepartmentController {

    private final IDepartmentService departmentService;

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(
            summary = "Crear un nuevo departamento",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Departamento creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
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

    @Operation(
            summary = "Eliminar un departamento por ID",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Departamento eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar departamentos con paginación",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            parameters = {
                    @Parameter(name = "page", description = "Número de página (0-indexado)", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo para ordenar (ej: code,asc)", example = "code,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de departamentos")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping
    public Map<String, Object> findAllDepartment(@PageableDefault(size = 10, sort = "code") Pageable pagination) {
        Page<DepartmentResponseDTO> deparmentPage = departmentService.findAllDepartment(pagination);
        return PaginationResponseBuilder.build(deparmentPage);
    }

    @Operation(
            summary = "Obtener un departamento por su ID",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content)
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(
            summary = "Actualizar un departamento existente",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content)
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public DepartmentResponseDTO updateDepartment(
            @PathVariable Long id,
            @RequestBody @Valid DepartmentUpdateDTO departmentDto) {
        return departmentService.updateDepartment(id, departmentDto);
    }
}
