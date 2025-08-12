package com.example.OMS.controller;


import com.example.OMS.auth.utils.securityUtils;
import com.example.OMS.models.invoice;
import com.example.OMS.service.invoiceService;
import com.example.OMS.service.orderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.OMS.repository.invoiceRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class invoiceController {
    private final invoiceRepo invoice_Repo;
    private final invoiceService invoice_Service;
    private final securityUtils sec_utils;

    public invoiceController(invoiceRepo invoice_Repo, invoiceService invoice_Service, securityUtils sec_utils) {
        this.invoice_Repo = invoice_Repo;
        this.invoice_Service = invoice_Service;
        this.sec_utils = sec_utils;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllInvoices() {
        List<invoice> invoices = invoice_Service.getAllInvoices();
        BigDecimal totalAmount = invoice_Service.getTotalOfInvoices();

        return ResponseEntity.ok(Map.of(
                "invoices", invoices,
                "totalAmount", totalAmount
        ));
    }

    @GetMapping("orders/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<?> getInvoiceOfOrder(
            @PathVariable Long orderId,
            HttpServletRequest request) {

        invoice inv = invoice_Service.getInvoiceByOrderId(orderId);

        if (sec_utils.hasRole("CUSTOMER")) {
            sec_utils.validateCustomerAccess(request, inv.getOrder().getCustomer().getId());
        }

        return ResponseEntity.ok(inv);
    }
}