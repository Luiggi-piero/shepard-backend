package com.example.skilllinkbackend.features.roomtype.service;

import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomTypeService {
    RoomTypeResponseDTO createRoomType(RoomTypeRegisterDTO roomTypeDto);

    void deleteRoomType(Long id);

    Page<RoomTypeResponseDTO> findAll(Pageable pagination);
}
