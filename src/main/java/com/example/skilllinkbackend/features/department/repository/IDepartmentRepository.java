package com.example.skilllinkbackend.features.department.repository;

import com.example.skilllinkbackend.features.department.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {

    @Query("""
            SELECT d
            FROM Department d
            WHERE d.enabled = true
            AND d.id = :id
            """)
    Optional<Department> findById(Long id);

    @Query("""
            SELECT d
            FROM Department d
            WHERE d.enabled = true
            """)
    Page<Department> findAllDepartment(Pageable pagination);
}
