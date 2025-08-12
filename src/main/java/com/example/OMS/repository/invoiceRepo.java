package com.example.OMS.repository;

import com.example.OMS.models.invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface invoiceRepo extends JpaRepository<invoice, Long> {
    Optional<invoice> findByOrderId(Long orderId);
}
