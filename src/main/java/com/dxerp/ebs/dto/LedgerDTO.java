package com.dxerp.ebs.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LedgerDTO {
    private Long id;
    private String voucherNo;
    private Date voucherDate;
    private Long coaId;
    private String coaCode;
    private Double debitAmount;
    private Double creditAmount;
    private String refCoaCode;
    private String remarks;
    private Long createdBy;
}
