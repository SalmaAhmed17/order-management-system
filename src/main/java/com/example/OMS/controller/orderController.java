package com.example.OMS.controller;


import com.example.OMS.DTOs.*;
import com.example.OMS.enums.orderStatus;
import com.example.OMS.models.*;
import com.example.OMS.service.orderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.OMS.auth.utils.securityUtils;


@RestController
@RequestMapping("/api/orders")
public class orderController {
    private final orderService order_Service;
    private final securityUtils sec_utils;
    public orderController(orderService order_Service, securityUtils sec_utils) {
        this.order_Service = order_Service;
        this.sec_utils = sec_utils;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<order> createOrder(@RequestBody createOrderRequest req) {
        order o = order_Service.createOrder(req);
        return ResponseEntity.ok(o);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<order> updateStatus(@PathVariable Long id, @RequestParam orderStatus status) {
        order updated = order_Service.updateOrderStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<order>> getOrdersOfCustomer(
            @PathVariable Long customerId,
            HttpServletRequest request) {

        if (sec_utils.hasRole("CUSTOMER")) { //skip admins
            sec_utils.validateCustomerAccess(request, customerId);
        }

        List<order> orders = order_Service.getOrdersOfCustomer(customerId);
        return ResponseEntity.ok(orders);
    }
}

