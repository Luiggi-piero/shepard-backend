package com.example.skilllinkbackend.features.roomtype.service;

import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;
import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import com.example.skilllinkbackend.features.roomtype.repository.IRoomTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeService implements IRoomTypeService{

    private final IRoomTypeRepository roomTypeRepository;

    public RoomTypeService(IRoomTypeRepository roomTypeRepository){
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomTypeResponseDTO createRoomType(RoomTypeRegisterDTO roomTypeDto) {
        RoomType roomType = new RoomType(roomTypeDto);
        roomTypeRepository.save(roomType);
        return new RoomTypeResponseDTO(roomType);
    }
}
