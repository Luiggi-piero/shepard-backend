package com.example.skilllinkbackend.features.department.repository;

import com.example.skilllinkbackend.features.department.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
}
