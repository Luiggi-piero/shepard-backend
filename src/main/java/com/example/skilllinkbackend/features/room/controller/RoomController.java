package com.example.skilllinkbackend.features.room.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;
import com.example.skilllinkbackend.features.room.dto.RoomUpdateDTO;
import com.example.skilllinkbackend.features.room.service.IRoomService;
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
@RequestMapping("/rooms")
@SecurityRequirement(name = "bearer-key")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Habitaciones", description = "Operaciones relacionadas con la gestión de habitaciones")
public class RoomController {

    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(
            summary = "Crear una nueva habitación",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DataResponse<RoomResponseDTO>> createRoom(
            @RequestBody @Valid RoomRegisterDTO roomRegisterDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        RoomResponseDTO roomResponseDTO = roomService.createRoom(roomRegisterDTO);
        URI url = uriComponentsBuilder.path("/rooms/{id}").buildAndExpand(roomResponseDTO.id()).toUri();
        DataResponse<RoomResponseDTO> response = new DataResponse<>(
                "Habitación creada con éxito",
                HttpStatus.CREATED.value(),
                roomResponseDTO
        );
        return ResponseEntity.created(url).body(response);
    }

    @Operation(
            summary = "Eliminar una habitación por ID",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Habitación eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Habitación no encontrada", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar habitaciones con paginación",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            parameters = {
                    @Parameter(name = "page", description = "Número de página (0-indexado)", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10"),
                    @Parameter(name = "sort", description = "Campo para ordenar (ej: name,asc)", example = "number,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de habitaciones")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping
    public Map<String, Object> findAll(
            @PageableDefault(size = 10, sort = "number") Pageable pagination) {
        Page<RoomResponseDTO> roomPage = roomService.findAll(pagination);
        return PaginationResponseBuilder.build(roomPage);
    }

    @Operation(
            summary = "Obtener una habitación por su ID",
            description = "Solo accesible por usuarios con rol ADMIN o RECEPTION",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Habitación encontrada"),
                    @ApiResponse(responseCode = "404", description = "Habitación no encontrada", content = @Content)
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    @GetMapping("/{id}")
    public RoomResponseDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @Operation(
            summary = "Actualizar una habitación existente",
            description = "Solo accesible por usuarios con rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Habitación actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Habitación no encontrada", content = @Content)
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public RoomResponseDTO updateRoom(@PathVariable Long id, @RequestBody @Valid RoomUpdateDTO roomUpdateDTO) {
        return roomService.updateRoom(id, roomUpdateDTO);
    }
}
