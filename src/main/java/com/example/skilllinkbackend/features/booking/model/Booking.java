package com.example.skilllinkbackend.features.booking.model;

import com.example.skilllinkbackend.features.bookingitem.model.BookingItem;
import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import com.example.skilllinkbackend.features.usuario.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private User guest;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Receptionist receptionist;

    @Column(name = "check_in", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime checkIn;

    @Column(name = "check_out", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime checkOut;

    @Enumerated(EnumType.STRING)// Almacena el enum como String en la bd
    private ReservationStatus status;

    // Guarda la fecha de creación en UTC
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookingItem> bookingItems = new ArrayList<>();

    private boolean enabled = true;
}
