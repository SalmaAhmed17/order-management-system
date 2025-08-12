package com.example.OMS.controller;

import com.example.OMS.models.product;
import com.example.OMS.service.productService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productController {

    private final productService product_Service;

    public productController(productService product_Service) {
        this.product_Service = product_Service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<product> createProduct(@RequestBody product p) {

        return product_Service.createProduct(p)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<product> getOne(@PathVariable Long id) {
        return product_Service.getOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<product> update(@PathVariable Long id, @RequestBody product update) {
        product updatedProduct = product_Service.update(id, update);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        product_Service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    //search end point: /api/products?name=mouse&minPrice=10&maxPrice=50&page=0&size=10&sort=price,asc
    public ResponseEntity<List<product>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<product> page = product_Service.search(name, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(page.getContent());
    }

}
