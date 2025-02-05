package com.dxerp.ebs.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dxerp.ebs.dto.PaginatedResponse;
import com.dxerp.ebs.dto.PurchaseDTO;
import com.dxerp.ebs.dto.PurchaseDetailsDTO;
import com.dxerp.ebs.entity.Category;
import com.dxerp.ebs.entity.Purchase;
import com.dxerp.ebs.service.PurchaseService;
import com.dxerp.ebs.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public PurchaseDTO createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        return purchaseService.createPurchase(purchaseDTO);
    }

    @GetMapping
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

        @GetMapping("/list")
    // @PreAuthorize("hasPermission(null, 'categories/list')")
    public ResponseEntity<ApiResponse<PaginatedResponse<PurchaseDTO>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
     
        Pageable pageable = PageRequest.of(page, size);
        Page<PurchaseDTO> purchase = purchaseService.getPurchaseList(pageable);
        
        PaginatedResponse<PurchaseDTO> response = new PaginatedResponse<>(purchase.getContent(), purchase);
        return ResponseEntity.ok(new ApiResponse<>(true, "Purchase fetched successfully", response));
    }

    

    @GetMapping("/{id}")
    public PurchaseDTO getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/purchaseDetails/{id}")
    public PurchaseDTO getPurchaseDetails(@PathVariable Long id) {
        return purchaseService.getPurchaseWithDetails(id);
    }

    
}

