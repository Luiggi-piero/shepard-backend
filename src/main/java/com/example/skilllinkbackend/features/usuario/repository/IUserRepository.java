package com.example.skilllinkbackend.features.usuario.repository;

import com.example.skilllinkbackend.features.usuario.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u
            FROM User u
            WHERE u.enabled = true
            AND u.email = :email
            """)
    UserDetails findByEmail(String email);

    @Query("""
            SELECT COUNT(u) > 0
            FROM User u
            WHERE u.email = :email
            AND u.enabled = true
            """)
    boolean existsByEmail(String email);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.enabled = true
            AND u.userId = :userId
            """)
    Optional<User> findByUserId(Long userId);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.enabled = true AND u.email IN :emails
            """)
    List<User> findExistingEmails(List<String> emails);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.enabled = true
            """)
    Page<User> findAll(Pageable pagination);
}