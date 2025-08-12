package com.example.OMS.models;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@Getter @Setter
public class orderItemKey implements Serializable {
    private Long orderId;
    private Long productId;
}

