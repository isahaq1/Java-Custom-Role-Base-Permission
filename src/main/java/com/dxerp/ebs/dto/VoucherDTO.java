package com.dxerp.ebs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private Long id;
    private String type;
    private String voucherNo;
    private Long approveById;
    private Date approveDate;
    private Date voucherDate;
    private Date chequeDate;
    private String chequeNo;
    private String narration;
    private Integer voucherStatus;
    private Double totalAmount;
    private Long createById;
    private Date createDate;
    private Long companyId;
    private String companyCode;
    private Float amount;
    private LocalDateTime createdAt;
    private String description;
    private Integer status;
    private LocalDateTime updatedAt;
    private String voucherNumber;
    private Long vendorId;
    
    // Add additional fields for displaying vendor information if needed
    private String vendorName;
    private String vendorCode;
    
    // Add validation annotations if required
    public VoucherDTO(Long id, String voucherNumber) {
        this.id = id;
        this.voucherNumber = voucherNumber;
    }

    private List<VoucherDetailsDTO> voucherDetails;
} 