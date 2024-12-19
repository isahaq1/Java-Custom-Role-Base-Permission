package com.dxerp.ebs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.dto.CategoryDTO;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.repository.CategoryRepository;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  
     @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse<Category> createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);

        return new ApiResponse<>(true, "category Successfully Created", category);
    }

    public List<Category> getAllCategories() {
       return  categoryRepository.findAll();
        
    }

    @PreAuthorize("hasPermission(null, 'categories/delete')")
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));
                categoryRepository.delete(category);
    }

    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));
              
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return categoryRepository.save(category);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
        
    }


}
