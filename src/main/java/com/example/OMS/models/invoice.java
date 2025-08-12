package com.example.OMS.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "invoices")
@Data
@Getter @Setter
public class invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private order order;

    @Column(nullable=false)
    private BigDecimal amount;
    private OffsetDateTime invoice_date  = OffsetDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.OMS.models.order getOrder() {
        return order;
    }

    public void setOrder(com.example.OMS.models.order order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OffsetDateTime getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(OffsetDateTime invoice_date) {
        this.invoice_date = invoice_date;
    }
}
