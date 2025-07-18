package com.example.skilllinkbackend.features.accommodation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // indica que esta es una clase padre y que sus hijos se relacionan por medio del id, JOINED: el padre y sus hijos tienen sus propias tablas
@Table(name = "accommodations")
@Data
@NoArgsConstructor
public abstract class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer floor;
    private BigDecimal price;
    private boolean available = true;
    private boolean enabled = true;
}
