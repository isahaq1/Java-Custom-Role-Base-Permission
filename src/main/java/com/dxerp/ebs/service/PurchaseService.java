package com.dxerp.ebs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxerp.ebs.dto.PurchaseDTO;
import com.dxerp.ebs.entity.Purchase;

public interface PurchaseService {
    PurchaseDTO createPurchase(PurchaseDTO purchaseDTO);
    
    List<PurchaseDTO> getAllPurchases();
    PurchaseDTO getPurchaseById(Long id);
    PurchaseDTO getPurchaseWithDetails(Long id);
     Page<PurchaseDTO> getPurchaseList(Pageable pageable);
}
