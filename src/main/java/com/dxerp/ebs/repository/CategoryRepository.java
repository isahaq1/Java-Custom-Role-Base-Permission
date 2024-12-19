package com.dxerp.ebs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxerp.ebs.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
