package com.example.skilllinkbackend.features.role.repository;

import com.example.skilllinkbackend.features.role.model.Role;
import com.example.skilllinkbackend.features.role.model.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RolesEnum name);
}