package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.ProductDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO,MultipartFile productImage);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    
}
