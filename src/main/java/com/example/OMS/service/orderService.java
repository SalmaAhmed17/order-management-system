package com.example.OMS.service;


import com.example.OMS.enums.orderStatus;
import com.example.OMS.exceptions.InsufficientStockException;
import com.example.OMS.exceptions.ResourceNotFoundException;
import com.example.OMS.models.*;
import com.example.OMS.repository.*;
import com.example.OMS.DTOs.createOrderRequest;
import com.example.OMS.DTOs.orderItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import java.math.BigDecimal;
import java.util.*;

@Service
public class orderService {
    private final orderRepo order_Repo;
    private final productRepo product_Repo;
    private final customerRepo customer_Repo;
    private final invoiceRepo invoice_Repo;

    public orderService(orderRepo order_Repo, productRepo product_Repo,
                        customerRepo customer_Repo, invoiceRepo invoice_Repo) {
        this.order_Repo = order_Repo;
        this.product_Repo = product_Repo;
        this.customer_Repo = customer_Repo;
        this.invoice_Repo = invoice_Repo;
    }

    @Transactional
    public order createOrder(createOrderRequest request) {
        customer customer = customer_Repo.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        order order = new order();
        order.setCustomer(customer);
        List<orderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (orderItemRequest itReq : request.getItems()) {
            product product = product_Repo.findById(itReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itReq.getProductId()));

            if (product.getStock() < itReq.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            // deduct stock
            product.setStock(product.getStock() - itReq.getQuantity());
            product_Repo.save(product);

            orderItem item = new orderItem();
            item.setProduct(product);
            item.setQuantity(itReq.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itReq.getQuantity())));
            item.setOrder(order);

            items.add(item);
            total = total.add(item.getSubtotal());
        }

        order.setItems(items);
        order.setTotal(total);
        order.setStatus(orderStatus.CREATED);

        order saved = order_Repo.save(order);
        return saved;
    }

    @Transactional
    public order updateOrderStatus(Long orderId, orderStatus status) {
        order order = order_Repo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        order saved = order_Repo.save(order);

        if (status == orderStatus.COMPLETED) {
            // generate invoice if not exists
            invoice_Repo.findByOrderId(order.getId()).orElseGet(() -> {
                invoice inv = new invoice();
                inv.setOrder(order);
                inv.setAmount(order.getTotal());
                inv.setInvoice_date(OffsetDateTime.now());
                return invoice_Repo.save(inv);
            });
        }

        return saved;
    }

    public List<order> getOrdersOfCustomer(Long customerId) {
        List<order> orders = order_Repo.findByCustomerId(customerId);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("This customer is not associated with any orders yet!");
        }
        return orders;
    }
}

