package com.example.OMS.service;


import com.example.OMS.exceptions.ResourceNotFoundException;
import com.example.OMS.models.product;
import com.example.OMS.repository.productRepo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class productService {

    private final productRepo product_Repo;

    public productService(productRepo product_Repo) {
        this.product_Repo = product_Repo;
    }

    public Optional<product> createProduct(product p) {
        if (p.getPrice().compareTo(BigDecimal.ZERO) < 0 || p.getStock() < 0) {
            return Optional.empty();
        }
        return Optional.of(product_Repo.save(p));
    }

    public Optional<product> getOne(Long id) {
        return Optional.ofNullable(product_Repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found")));
    }

    public product update(Long id, product update) {
        return product_Repo.findById(id).map(existing -> {
            if (update.getName() != null) {
                existing.setName(update.getName());
            }
            existing.setDescription(update.getDescription());
            existing.setPrice(update.getPrice());
            if (update.getStock() != null) {
                existing.setStock(update.getStock());
            }
            return product_Repo.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }


    public void delete(Long id) {
        if (!product_Repo.existsById(id)) {
            throw new ResourceNotFoundException("product not found");
        }
        product_Repo.deleteById(id);
    }


    public Page<product> search(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<product> spec = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (minPrice != null) {
            spec = spec.and((root, q, cb) -> cb.ge(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, q, cb) -> cb.le(root.get("price"), maxPrice));
        }

        return product_Repo.findAll(spec, pageable);
    }

}
