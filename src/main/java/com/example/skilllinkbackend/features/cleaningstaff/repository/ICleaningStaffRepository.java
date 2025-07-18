package com.example.skilllinkbackend.features.cleaningstaff.repository;

import com.example.skilllinkbackend.features.cleaningstaff.model.CleaningStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICleaningStaffRepository extends JpaRepository<CleaningStaff, Long> {
}
