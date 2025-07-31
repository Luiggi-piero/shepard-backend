package com.example.skilllinkbackend.features.accommodation.repository;

import com.example.skilllinkbackend.features.accommodation.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {
}
