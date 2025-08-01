package com.example.skilllinkbackend.features.booking.repository;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.enabled = true
            AND b.status = :reservationStatus
            """)
    List<Booking> findAllByStatus(ReservationStatus reservationStatus);

    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.enabled = true
            AND b.id = :id
            """)
    Optional<Booking> findByIdAndEnabledTrue(Long id);
}
