package com.example.skilllinkbackend.features.room.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;
import com.example.skilllinkbackend.features.room.service.IRoomService;
import com.example.skilllinkbackend.shared.util.PaginationResponseBuilder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class RoomController {

    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Map<String, Object> findAll(
            @PageableDefault(size = 10, sort = "number") Pageable pagination) {
        Page<RoomResponseDTO> roomPage = roomService.findAll(pagination);
        return PaginationResponseBuilder.build(roomPage);
    }

    @GetMapping("/{id}")
    public RoomResponseDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }
}
