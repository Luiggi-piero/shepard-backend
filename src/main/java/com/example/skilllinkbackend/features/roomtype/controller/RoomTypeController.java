package com.example.skilllinkbackend.features.roomtype.controller;

import com.example.skilllinkbackend.config.responses.DataResponse;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;
import com.example.skilllinkbackend.features.roomtype.service.IRoomTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/room-types")
@AllArgsConstructor
public class RoomTypeController {

    private final IRoomTypeService roomTypeService;

    @SecurityRequirement(name = "bearer-key")
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
}
