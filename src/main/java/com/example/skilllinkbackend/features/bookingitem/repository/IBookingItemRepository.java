package com.example.skilllinkbackend.features.bookingitem.repository;

import com.example.skilllinkbackend.features.bookingitem.model.BookingItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingItemRepository extends JpaRepository<BookingItem, Long> {

    // Esta consulta modifica datos, así que ejecútala dentro de una transacción y no esperes un resultado.
    @Modifying
    @Transactional
    @Query("""
            DELETE
            FROM BookingItem bi
            WHERE bi.id IN :ids
            """)
    void deleteAllByIdIn(List<Long> ids);
}
