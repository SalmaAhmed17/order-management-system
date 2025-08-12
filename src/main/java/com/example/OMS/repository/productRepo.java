package com.example.OMS.repository;

import com.example.OMS.models.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepo extends JpaRepository<product, Long>, JpaSpecificationExecutor<product> { }
