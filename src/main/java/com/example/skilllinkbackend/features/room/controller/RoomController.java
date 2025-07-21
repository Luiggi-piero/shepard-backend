package com.example.skilllinkbackend.features.room.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;
import com.example.skilllinkbackend.features.room.service.IRoomService;
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
}
