package com.example.OMS.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@Getter @Setter
public class orderItem {

    @EmbeddedId
    private orderItemKey id = new orderItemKey();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private product product;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal subtotal;

    public orderItemKey getId() {
        return id;
    }

    public void setId(orderItemKey id) {
        this.id = id;
    }

    public com.example.OMS.models.order getOrder() {
        return order;
    }

    public void setOrder(com.example.OMS.models.order order) {
        this.order = order;
    }

    public com.example.OMS.models.product getProduct() {
        return product;
    }

    public void setProduct(com.example.OMS.models.product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
