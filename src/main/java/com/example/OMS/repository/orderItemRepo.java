package com.example.OMS.repository;

import com.example.OMS.models.orderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderItemRepo extends JpaRepository<orderItem, Long> { }

