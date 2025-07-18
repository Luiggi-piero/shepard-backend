package com.example.skilllinkbackend.features.securitystaff.repository;

import com.example.skilllinkbackend.features.securitystaff.model.SecurityStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISecurityStaffRepository extends JpaRepository<SecurityStaff, Long> {
}
