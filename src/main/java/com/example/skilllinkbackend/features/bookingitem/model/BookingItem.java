package com.example.skilllinkbackend.features.bookingitem.model;

import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import com.example.skilllinkbackend.features.booking.model.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_items")
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // Muchos items de reserva pueden tener un mismo alojamiento(room o department)
    // pero en distintos intervalos de tiempo
    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    private BigDecimal price;
}
