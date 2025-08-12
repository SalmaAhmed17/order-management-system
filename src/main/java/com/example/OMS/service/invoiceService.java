package com.example.OMS.service;

import com.example.OMS.exceptions.ResourceNotFoundException;
import com.example.OMS.models.invoice;
import com.example.OMS.repository.invoiceRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class invoiceService {

    private final invoiceRepo invoice_Repo;

    public invoiceService(invoiceRepo invoice_Repo) {
        this.invoice_Repo = invoice_Repo;
    }

    public invoice getInvoiceByOrderId(Long orderId) {

        return invoice_Repo.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("No invoice found for order ID: " + orderId));
    }

    public List<invoice> getAllInvoices() {
        return invoice_Repo.findAll();
    }

    public BigDecimal getTotalOfInvoices() {
        return invoice_Repo.findAll().stream()
                .map(invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
