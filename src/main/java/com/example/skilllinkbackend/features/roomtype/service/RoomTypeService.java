package com.example.skilllinkbackend.features.roomtype.service;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeRegisterDTO;
import com.example.skilllinkbackend.features.roomtype.dto.RoomTypeResponseDTO;
import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import com.example.skilllinkbackend.features.roomtype.repository.IRoomTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeService implements IRoomTypeService {

    private final IRoomTypeRepository roomTypeRepository;

    public RoomTypeService(IRoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomTypeResponseDTO createRoomType(RoomTypeRegisterDTO roomTypeDto) {
        RoomType roomType = new RoomType(roomTypeDto);
        roomTypeRepository.save(roomType);
        return new RoomTypeResponseDTO(roomType);
    }

    @Override
    public void deleteRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        roomType.deactive();
    }

    @Override
    public Page<RoomTypeResponseDTO> findAll(Pageable pagination) {
        return roomTypeRepository.findAll(pagination).map(RoomTypeResponseDTO::new);
    }

    @Override
    public RoomTypeResponseDTO findById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        return new RoomTypeResponseDTO(roomType);
    }
}
