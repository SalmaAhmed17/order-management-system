package com.example.OMS.repository;

import com.example.OMS.models.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderRepo extends JpaRepository<order, Long> {
    List<order> findByCustomerId(Long customerId);
}