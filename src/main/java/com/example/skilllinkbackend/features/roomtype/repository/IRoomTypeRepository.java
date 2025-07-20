package com.example.skilllinkbackend.features.roomtype.repository;

import com.example.skilllinkbackend.features.roomtype.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Query("""
            SELECT r
            FROM RoomType r
            WHERE r.enabled = true
            AND r.id = :id
            """)
    Optional<RoomType> findById(Long id);
}
