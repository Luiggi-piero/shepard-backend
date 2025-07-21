package com.example.skilllinkbackend.features.room.repository;

import com.example.skilllinkbackend.features.room.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT r
            FROM Room r
            WHERE r.id = :id
            AND r.enabled = true
            """)
    Optional<Room> findById(Long id);

    @Query("""
            SELECT r
            FROM Room r
            WHERE r.enabled = true
            """)
    Page<Room> findAll(Pageable pagination);
}
