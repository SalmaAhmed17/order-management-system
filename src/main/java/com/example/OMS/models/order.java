package com.example.OMS.models;


import com.example.OMS.enums.orderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="customer_id")
    @JsonBackReference
    private customer customer;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<orderItem> items;

    @Enumerated(EnumType.STRING)
    private orderStatus status = orderStatus.CREATED;

    @Column(nullable=false)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private invoice invoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.OMS.models.customer getCustomer() {
        return customer;
    }

    public void setCustomer(com.example.OMS.models.customer customer) {
        this.customer = customer;
    }

    public List<orderItem> getItems() {
        return items;
    }

    public void setItems(List<orderItem> items) {
        this.items = items;
    }

    public orderStatus getStatus() {
        return status;
    }

    public void setStatus(orderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public com.example.OMS.models.invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(com.example.OMS.models.invoice invoice) {
        this.invoice = invoice;
    }
}
