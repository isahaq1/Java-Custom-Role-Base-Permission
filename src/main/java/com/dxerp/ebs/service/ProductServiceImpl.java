package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.ProductDTO;
import com.dxerp.ebs.entity.Product;
import com.dxerp.ebs.entity.User;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.entity.Model;
import com.dxerp.ebs.repository.ProductRepository;
import com.dxerp.ebs.repository.CategoryRepository;
import com.dxerp.ebs.repository.ModelRepository;
import com.dxerp.ebs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

   
  

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ProductRepository productRepository;
    private static final String UPLOAD_DIR = "uploads/products/";

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        copyProperties(productDTO, product);
        product = productRepository.save(product);
        return toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile productImage) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        updateProperties(productDTO, product,productImage);
        product = productRepository.save(product);
        return toDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private void copyProperties(ProductDTO source, Product target) {
        target.setName(source.getName());
        target.setCode(source.getCode());
        Category category = categoryRepository.findById(source.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        target.setCategory(category);
        Model model = modelRepository.findById(source.getModelId())
        .orElseThrow(() -> new RuntimeException("Model not found"));
        target.setModel(model);
        target.setPrice(source.getPrice());
        target.setDetails(source.getDetails());
        target.setStatus(source.getStatus());
        target.setCreatedBy(source.getCreatedBy());
     
    }

    private void updateProperties(ProductDTO source, Product target,MultipartFile productImage) {
        target.setName(source.getName());
        target.setCode(source.getCode());
        Category category = categoryRepository.findById(source.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        target.setCategory(category);
        Model model = modelRepository.findById(source.getModelId())
        .orElseThrow(() -> new RuntimeException("Model not found"));
        target.setModel(model);
        target.setPrice(source.getPrice());
        target.setDetails(source.getDetails());

        target.setStatus(source.getStatus());
        target.setCreatedBy(source.getCreatedBy());
        // if (source.getImageFile() != null && !source.getImageFile().isEmpty()) {
        //     String fileName = saveImage(source.getImageFile());
        //     target.setProductImage(fileName); // Save the file name in the database
        // }

        if (productImage != null && !productImage.isEmpty()) {
            // Logic to save the file (e.g., to local storage, S3, etc.)
            String filePath = saveImage(productImage);
            target.setProductImage(filePath);
        }
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCode(product.getCode());
        dto.setCategoryId(product.getCategory().getId().longValue());
        dto.setModelId(product.getId().longValue());
        dto.setModel(product.getModel());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setDetails(product.getDetails());
        dto.setStatus(product.getStatus());
        dto.setCreatedBy(product.getCreatedBy());
        return dto;
    }

      @PreAuthorize("hasPermission(null, 'products/list')")
    public Page<Product> getAllProductList(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

         private String saveImage(MultipartFile file) {
        // Example: Save the file locally (can replace with cloud storage logic)
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            // Path filePath = Paths.get("uploads/" + fileName);
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return "/products/" + fileName;
            // return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save product image", e);
        }
    }
}