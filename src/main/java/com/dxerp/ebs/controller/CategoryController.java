package com.dxerp.ebs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxerp.ebs.dto.CategoryDTO;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.service.CategoryService;
import com.dxerp.ebs.util.ApiResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
     @Autowired
    private CategoryService categoryService;
       public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

     @PostMapping("/create")
      @PreAuthorize("hasPermission(null, 'categories/create')")
      public ResponseEntity<ApiResponse<Category>> registerUser(@RequestBody CategoryDTO categoryDTO) {
         ApiResponse<Category> response = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getModelsDropdown() {
     
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, "Categories fetched successfully", categories));
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission(null, 'categories/list')")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
     
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, "Categories fetched successfully", categories));
    }

        @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getUserById(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

     @PutMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'categories/edit')")
     public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "category updated successfully", updatedCategory));
    }

      @DeleteMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'categories/delete')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted successfully", null));
    }
}
