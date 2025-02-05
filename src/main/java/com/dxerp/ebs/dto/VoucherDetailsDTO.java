package com.dxerp.ebs.dto;

import lombok.Data;
import java.util.Date;

@Data
public class VoucherDetailsDTO {
    private Long id;
    private Long accCoaId;
    private Long accCostCenterId;
    private Long accVoucherId;
    private Double creditAmountBk;
    private Double debitAmountBk;
    private Long masterKey;
    private String masterRef;
    private Long refAccCoaId;
    private Long slNo;
    private String ledgerText;
    private String lineText;
    private Long accFlexId;
    private Long accProfitCenterId;
    private Long bpId;
    private String bpCode;
    private String ccCode;
    private String glCode;
    private String orderNo;
    private String pcCode;
    private String spGlCode;
    private Date reconcileDate;
    private Double debitAmount;
    private Double creditAmount;
    private String docDetailFile;
} 