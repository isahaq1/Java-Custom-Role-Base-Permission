package com.dxerp.ebs.dto;

import lombok.Data;

import java.util.List;

import com.dxerp.ebs.entity.Location;
import com.dxerp.ebs.entity.Vendor;

@Data
public class PurchaseDTO {
    private Long id;
    private String purchaseDate;
    private Double totalAmount;
    private Integer vendorId;
    private Integer warehouseId;
    private Double totalVat;
    private Double totalTax;
    private Double totalDiscount;
    private String createdBy;
    private String updatedBy;
    private Vendor vendor;
    private Location location;
    private List<PurchaseDetailsDTO> purchaseDetails;
}