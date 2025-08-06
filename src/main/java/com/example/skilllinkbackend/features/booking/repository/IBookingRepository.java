package com.example.skilllinkbackend.features.booking.repository;

import com.example.skilllinkbackend.features.booking.model.Booking;
import com.example.skilllinkbackend.features.booking.model.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository
        extends JpaRepository<Booking, Long>, BookingRepositoryCustom {

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

    /*
    // Sin criteria API
    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.enabled = true
            AND (:status IS NULL OR b.status = :status)
            AND (:guestId IS NULL OR b.guest.userId = :guestId)
            AND (:receptionistId IS NULL OR b.receptionist.id = :receptionistId)
            AND b.checkIn >= :checkIn
            """)
    Page<Booking> findAllBooking(
            Pageable pageable,
            @Param("status") ReservationStatus status,
            @Param("guestId") Long guestId,
            @Param("receptionistId") Long receptionistId,
            @Param("checkIn") OffsetDateTime checkIn
    );*/

    /*
    AND (:checkOut IS NULL OR b.checkOut <= :checkOut)


    */

    /*,
            @Param("checkOut") OffsetDateTime checkOut,


            */
}
