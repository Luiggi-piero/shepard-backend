package com.example.skilllinkbackend.features.room.service;

import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService {
    RoomResponseDTO createRoom(RoomRegisterDTO roomRegisterDTO);

    void deleteRoomById(Long id);

    Page<RoomResponseDTO> findAll(Pageable pagination);

    RoomResponseDTO findById(Long id);
}
