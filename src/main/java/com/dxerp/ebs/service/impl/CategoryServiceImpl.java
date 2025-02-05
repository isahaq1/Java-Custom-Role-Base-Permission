package com.dxerp.ebs.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dxerp.ebs.dto.CategoryDTO;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.repository.CategoryRepository;
import com.dxerp.ebs.service.CategoryService;
import com.dxerp.ebs.util.ApiResponse;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ApiResponse<Category> createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setStatus(true);
        categoryRepository.save(category);
        return new ApiResponse<>(true, "Category Successfully Created", category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission(null, 'categories/delete')")
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    @Override
    public Page<Category> getAllCategoriesWithPagination(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
} 