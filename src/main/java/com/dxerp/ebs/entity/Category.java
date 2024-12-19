package com.dxerp.ebs.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "status", nullable = false)
    private boolean status = true;
    
    // Getters and Setters
    public String getName() {
    return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getStatus() {
        return status;
        }
        
    public void setStatus(Boolean status) {
        this.status = status;
    }
}
