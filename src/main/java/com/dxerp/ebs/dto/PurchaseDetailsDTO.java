package com.dxerp.ebs.dto;

import lombok.Data;

@Data
public class PurchaseDetailsDTO {
    private Long productId;
    private Double rate;
    private Integer qty;
    private Double vat;
    private Double tax;
    private Double discount;
    private Double total;
    private ProductDTO product;

    public void setProduct(ProductDTO product) {

        this.product = product;

    }

    public ProductDTO getProduct() {

        return product;

    }

  
    
}