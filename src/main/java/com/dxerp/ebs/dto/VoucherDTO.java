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
    private String paymentType;
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
    
    // Add validation annotations if required
    public VoucherDTO(Long id, String voucherNo, Date voucherDate, Double totalAmount, String type, Integer status, String narration) {
        this.id = id;
        this.voucherNo = voucherNo;
        this.voucherDate = voucherDate;
        this.totalAmount = totalAmount;
        this.type = type;
        this.status = status;
        this.narration = narration;
    }

    private List<VoucherDetailsDTO> voucherDetails;
} 