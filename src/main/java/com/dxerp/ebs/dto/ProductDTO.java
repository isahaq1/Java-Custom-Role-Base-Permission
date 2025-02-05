package com.dxerp.ebs.dto;
import com.dxerp.ebs.entity.Category;

import org.springframework.web.multipart.MultipartFile;

import com.dxerp.ebs.entity.Model;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String code;
    private Long categoryId;
    private Long modelId;
    private Double price;
    private String details;
    private MultipartFile productImage;
    private String status;
    private Long createdBy;
    private Model model;
    private Category category;

    public Long getCategoryId() {
        return categoryId;
    }
 
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getModelId() {
        return modelId;
    }
 
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}