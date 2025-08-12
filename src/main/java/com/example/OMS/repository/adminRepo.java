package com.example.OMS.repository;

import com.example.OMS.models.admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface adminRepo extends JpaRepository<admin, Long> { }

