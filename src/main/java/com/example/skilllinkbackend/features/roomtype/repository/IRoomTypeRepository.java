package com.example.skilllinkbackend.features.roomtype.repository;

import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomTypeRepository extends JpaRepository<RoomType, Long> {
}
