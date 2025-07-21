package com.example.skilllinkbackend.features.roomtype.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeUpdateDTO;
import com.example.skilllinkbackend.features.roomtype.service.IRoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/room-types")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Tipos de habitaciones", description = "Operaciones relacionadas con la gestión de tipos de habitaciones")
public class RoomTypeController {

    private final IRoomTypeService roomTypeService;

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tipo de habitación creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DataResponse<RoomTypeResponseDTO>> createRoomType(
            @RequestBody @Valid RoomTypeRegisterDTO roomTypeRegisterDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.createRoomType(roomTypeRegisterDTO);
        URI url = uriComponentsBuilder.path("/room-types/{id}").buildAndExpand(roomTypeResponseDTO.id()).toUri();
        DataResponse<RoomTypeResponseDTO> response = new DataResponse<>(
                "Tipo de habitación creado con éxito",
                HttpStatus.CREATED.value(),
                roomTypeResponseDTO
        );
        return ResponseEntity.created(url).body(response);
    }

    @Operation(
            summary = "Eliminar un tipo de habitación por ID",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tipo de habitación eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Tipo de habitación no encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar tipos de habitaciones con paginación",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            parameters = {
                    @Parameter(name = "page", description = "Número de página (0-indexado)", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo para ordenar (ej: name,asc)", example = "name,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tipos de habitaciones")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping
    public Map<String, Object> findAll(@PageableDefault(size = 10, sort = "name") Pageable pagination) {
        Page<RoomTypeResponseDTO> roomTypePage = roomTypeService.findAll(pagination);
        Map<String, Object> response = new HashMap<>();
        response.put("content", roomTypePage.getContent());
        response.put("currentPage", roomTypePage.getNumber());
        response.put("totalItems", roomTypePage.getTotalElements());
        response.put("totalPages", roomTypePage.getTotalPages());
        response.put("pageSize", roomTypePage.getSize());
        response.put("hasNext", roomTypePage.hasNext());
        response.put("hasPrevious", roomTypePage.hasPrevious());
        return response;
    }

    @Operation(
            summary = "Obtener un tipo de habitación por su ID",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo de habitación encontrado"),
                    @ApiResponse(responseCode = "404", description = "Tipo de habitación no encontrado", content = @Content)
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping("/{id}")
    public RoomTypeResponseDTO findById(@PathVariable Long id) {
        return roomTypeService.findById(id);
    }

    @Operation(
            summary = "Actualizar un tipo de habitación existente",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo de habitación actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Tipo de habitación no encontrada", content = @Content)
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public RoomTypeResponseDTO updateRoomType(
            @PathVariable Long id,
            @RequestBody @Valid RoomTypeUpdateDTO roomTypeDto) {
        return roomTypeService.updateRoomType(id, roomTypeDto);
    }
}
