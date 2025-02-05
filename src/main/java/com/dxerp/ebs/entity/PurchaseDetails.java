package com.dxerp.ebs.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PurchaseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double rate;
    private Integer qty;
    private Double vat;
    private Double tax;
    private Double discount;
    private Double total;
    public Long getProductId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductId'");
    }
  
}
