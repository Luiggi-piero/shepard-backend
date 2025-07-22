package com.example.skilllinkbackend.features.room.service;

import com.example.skilllinkbackend.config.exceptions.NotFoundException;
import com.example.skilllinkbackend.features.room.dto.RoomRegisterDTO;
import com.example.skilllinkbackend.features.room.dto.RoomResponseDTO;
import com.example.skilllinkbackend.features.room.dto.RoomUpdateDTO;
import com.example.skilllinkbackend.features.room.model.Room;
import com.example.skilllinkbackend.features.room.repository.IRoomRepository;
import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import com.example.skilllinkbackend.features.roomtype.repository.IRoomTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService {

    private final IRoomRepository roomRepository;
    private final IRoomTypeRepository roomTypeRepository;

    public RoomService(IRoomRepository roomRepository, IRoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public RoomResponseDTO createRoom(RoomRegisterDTO roomRegisterDTO) {
        Room room = new Room(roomRegisterDTO);
        RoomType roomType = roomTypeRepository.findById(roomRegisterDTO.roomTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        room.setType(roomType);
        Room roomDb = roomRepository.save(room);
        return new RoomResponseDTO(roomDb);
    }

    @Override
    public void deleteRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        room.deactive();
    }

    @Override
    public Page<RoomResponseDTO> findAll(Pageable pagination) {
        return roomRepository.findAll(pagination).map(RoomResponseDTO::new);
    }

    @Override
    public RoomResponseDTO findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        return new RoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO updateRoom(Long id, RoomUpdateDTO roomUpdateDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        RoomType roomType = roomTypeRepository.findById(roomUpdateDTO.roomTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrada"));
        room.update(roomUpdateDTO);
        room.setType(roomType);
        return new RoomResponseDTO(room);
    }
}
