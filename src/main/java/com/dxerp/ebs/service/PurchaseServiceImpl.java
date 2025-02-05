package com.dxerp.ebs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxerp.ebs.dto.ProductDTO;
import com.dxerp.ebs.dto.PurchaseDTO;
import com.dxerp.ebs.dto.PurchaseDetailsDTO;
import com.dxerp.ebs.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dxerp.ebs.entity.Location;
import com.dxerp.ebs.entity.Product;
import com.dxerp.ebs.entity.Purchase;
import com.dxerp.ebs.entity.PurchaseDetails;
import com.dxerp.ebs.entity.Vendor;
import com.dxerp.ebs.repository.ProductRepository;
import com.dxerp.ebs.repository.PurchaseDetailsRepository;
import com.dxerp.ebs.repository.PurchaseRepository;
import com.dxerp.ebs.entity.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailsRepository purchaseDetailsRepository;
    private final ProductRepository productRepository;
    private JdbcTemplate jdbcTemplate;


    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               PurchaseDetailsRepository purchaseDetailsRepository,
                               ProductRepository productRepository,JdbcTemplate jdbcTemplate) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseDetailsRepository = purchaseDetailsRepository;
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    @Transactional
    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User authUser = customUserDetails.getUser();
   

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(purchaseDTO.getPurchaseDate());
        purchase.setTotalAmount(purchaseDTO.getTotalAmount());
        purchase.setTotalVat(purchaseDTO.getTotalVat());
        purchase.setTotalTax(purchaseDTO.getTotalTax());
        purchase.setTotalDiscount(purchaseDTO.getTotalDiscount());
        purchase.setCreatedBy(purchaseDTO.getCreatedBy());
        purchase.setUpdatedBy(purchaseDTO.getUpdatedBy());
         Vendor vendor = new Vendor();
        vendor.setId(purchaseDTO.getVendorId());
        purchase.setVendor(vendor);
        Location location = new Location();
        location.setId(purchaseDTO.getWarehouseId());
        purchase.setLocation(location);
        purchase = purchaseRepository.save(purchase);

        for (PurchaseDetailsDTO detailsDTO : purchaseDTO.getPurchaseDetails()) {
            Product product = productRepository.findById(detailsDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            PurchaseDetails purchaseDetails = new PurchaseDetails();
            purchaseDetails.setPurchase(purchase);
            purchaseDetails.setProduct(product);
            purchaseDetails.setRate(detailsDTO.getRate());
            purchaseDetails.setQty(detailsDTO.getQty());
            purchaseDetails.setVat(detailsDTO.getVat());
            purchaseDetails.setTax(detailsDTO.getTax());
            purchaseDetails.setDiscount(detailsDTO.getDiscount());
            purchaseDetails.setTotal(detailsDTO.getTotal());

            purchaseDetailsRepository.save(purchaseDetails);
            jdbcTemplate.update(
                "BEGIN stock_management_pkg.purchase_product(?, ?, ?, ?); END;",
                new Object[]{detailsDTO.getProductId(), purchaseDTO.getVendorId(), detailsDTO.getQty(),  authUser.getId()}
            );
        }
        

         return mapToDTO(purchase);
    }

    @Override
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        return mapToDTO(purchase);
    }

    private PurchaseDTO mapToDTO(Purchase purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getId());
        dto.setPurchaseDate(purchase.getPurchaseDate().toString());
        dto.setTotalAmount(purchase.getTotalAmount());
        dto.setTotalVat(purchase.getTotalVat());
        dto.setTotalTax(purchase.getTotalTax());
        dto.setTotalDiscount(purchase.getTotalDiscount());
        dto.setCreatedBy(purchase.getCreatedBy());
        dto.setVendorId(purchase.getVendor().getId());
        dto.setVendor(purchase.getVendor());
        dto.setWarehouseId(purchase.getLocation().getId());
        dto.setLocation(purchase.getLocation());
        dto.setUpdatedBy(purchase.getUpdatedBy());

        dto.setPurchaseDetails(
                purchase.getPurchaseDetails()
                        .stream()
                        .map(details -> {
                            PurchaseDetailsDTO detailsDTO = new PurchaseDetailsDTO();
                            detailsDTO.setProductId(details.getProduct().getId().longValue());
                            detailsDTO.setRate(details.getRate());
                            detailsDTO.setQty(details.getQty());
                            detailsDTO.setVat(details.getVat());
                            detailsDTO.setTax(details.getTax());
                            detailsDTO.setDiscount(details.getDiscount());
                            detailsDTO.setTotal(details.getTotal());
                            return detailsDTO;
                        })
                        .collect(Collectors.toList())
        );

        return dto;
    }
   

@Override
public PurchaseDTO getPurchaseWithDetails(Long id) {
    Purchase purchase = purchaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase not found"));

    PurchaseDTO purchaseDTO = mapToDTO(purchase);

    List<PurchaseDetailsDTO> purchaseDetailsDTOList = purchase.getPurchaseDetails()
            .stream()
            .map(details -> {
                Product product = details.getProduct();
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setCode(product.getCode());
                productDTO.setCategory(product.getCategory());
                productDTO.setModel(product.getModel());
                productDTO.setPrice(product.getPrice());
                productDTO.setDetails(product.getDetails());
                productDTO.setStatus(product.getStatus());
                productDTO.setCreatedBy(product.getCreatedBy());

                PurchaseDetailsDTO detailsDTO = new PurchaseDetailsDTO();
                detailsDTO.setRate(details.getRate());
                detailsDTO.setQty(details.getQty());
                detailsDTO.setVat(details.getVat());
                detailsDTO.setTax(details.getTax());
                detailsDTO.setDiscount(details.getDiscount());
                detailsDTO.setTotal(details.getTotal());
                detailsDTO.setProduct(productDTO);

                return detailsDTO;
            })
            .collect(Collectors.toList());

    purchaseDTO.setPurchaseDetails(purchaseDetailsDTOList);

    return purchaseDTO;
}

 @Override
    public Page<PurchaseDTO> getPurchaseList(Pageable pageable) {
        return purchaseRepository.findAll(pageable)
                .map(this::mapToDTO);
    }
}
