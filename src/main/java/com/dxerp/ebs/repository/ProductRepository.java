package com.dxerp.ebs.repository;

import com.dxerp.ebs.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
