package com.example.OMS.DTOs;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class createOrderRequest {
    private Long customerId;
    private List<orderItemRequest> items;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<orderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<orderItemRequest> items) {
        this.items = items;
    }
}

