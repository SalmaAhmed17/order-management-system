package com.example.OMS.repository;


import com.example.OMS.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface customerRepo extends JpaRepository<customer, Long> {
    Optional<customer> findByEmail(String email);
    Optional<customer> findByUsers_Id(Long userId);}








