package com.example.skilllinkbackend.features.room.service;

import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;

public interface IRoomService {
    RoomResponseDTO createRoom(RoomRegisterDTO roomRegisterDTO);

    void deleteRoomById(Long id);
}
