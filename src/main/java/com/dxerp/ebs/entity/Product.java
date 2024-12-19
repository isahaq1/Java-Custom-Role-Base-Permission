package com.dxerp.ebs.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false) // Foreign key
    private Model model;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Foreign key
    private Category category;

    @Column(nullable = true)
    private String details;

    @Column(nullable = true)
    private Number price;

    @Column(name = "product_image") // Stores the URL or file path
    private String productImage;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
