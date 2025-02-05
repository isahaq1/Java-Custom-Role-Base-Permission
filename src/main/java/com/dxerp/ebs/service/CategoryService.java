package com.dxerp.ebs.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dxerp.ebs.dto.CategoryDTO;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.util.ApiResponse;

public interface CategoryService {
    ApiResponse<Category> createCategory(CategoryDTO categoryDTO);
    List<Category> getAllCategories();
    void deleteCategory(Long id);
    Category updateCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(Long id);
    Page<Category> getAllCategoriesWithPagination(Pageable pageable);
}
