package com.dxerp.ebs.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Entity
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long productId;
    private String transactionType;  // Purchase, Sale, etc.
    private BigDecimal quantity;
    private Long sourceLocationId;
    private Long destinationLocationId;
    private String remarks;

    // Getters and Setters
}
