package com.example.skilllinkbackend.features.roomtype.service;

import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;

public interface IRoomTypeService {
    RoomTypeResponseDTO createRoomType(RoomTypeRegisterDTO roomTypeDto);

    void deleteRoomType(Long id);
}
