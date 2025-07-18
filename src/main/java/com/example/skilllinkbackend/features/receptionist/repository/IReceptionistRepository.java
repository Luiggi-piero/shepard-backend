package com.example.skilllinkbackend.features.receptionist.repository;

import com.example.skilllinkbackend.features.receptionist.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReceptionistRepository extends JpaRepository<Receptionist, Long> {
}
