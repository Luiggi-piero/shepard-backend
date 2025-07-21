package com.example.skilllinkbackend.features.room.repository;

import com.example.skilllinkbackend.features.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
}
