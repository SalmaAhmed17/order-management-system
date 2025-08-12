package com.example.OMS.repository;

import com.example.OMS.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);
}
