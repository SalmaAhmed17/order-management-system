package com.example.OMS.controller;

import com.example.OMS.auth.utils.securityUtils;
import com.example.OMS.models.customer;
import com.example.OMS.service.customerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class customerController {

    private final customerService cus_Service;
    private final securityUtils sec_utils;

    public customerController(customerService cus_Service, securityUtils sec_utils) {
        this.cus_Service = cus_Service;
        this.sec_utils = sec_utils;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<customer>> getAllCustomers() {
        List<customer> customers = cus_Service.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<customer> getCustomerById(
            @PathVariable Long id,
            HttpServletRequest request) {

        if (sec_utils.hasRole("CUSTOMER")) {
            sec_utils.validateCustomerAccess(request, id);
        }
        customer cus = cus_Service.getCustomerById(id);
        return ResponseEntity.ok(cus);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        cus_Service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
