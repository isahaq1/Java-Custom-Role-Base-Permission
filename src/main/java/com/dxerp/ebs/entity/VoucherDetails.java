package com.dxerp.ebs.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import oracle.sql.NUMBER;

import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOUCHER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id; 
    
    @Column(name = "ACC_COA_ID")
     private Long accCoaId;
     @Column(name = "ACC_COST_CENTER_ID")
     private Long accCostCenterId;
    @Column(name = "ACC_VOUCHER_ID")
    private Long accVoucherId;
    @Column(name = "CREDIT_AMOUNT_BK")
     private Double creditAmountBk;
    @Column(name = "DEBIT_AMOUNT_BK")
     private Double debitAmountBk;
    @Column(name = "MASTER_KEY")
     private Long masterKey;
     @Column(name = "MASTER_REF")
     private String masterRef;
    @Column(name = "REF_ACC_COA_ID")
     private Long refAccCoaId;
    @Column(name = "SL_NO")
     private Long slNo;
    @Column(name = "LEDGER_TEXT")
     private String ledgerText;
    @Column(name = "LINE_TEXT")
     private String lineText;
    @Column(name = "ACC_FLEX_ID")
     private Long accFlexId;
    @Column(name = "ACC_PROFIT_CENTER_ID")
     private Long accProfitCenterId;
    @Column(name = "BP_ID")
     private Long bpId;
    @Column(name = "BP_CODE")
     private String bpCode;
    @Column(name = "CC_CODE")
     private String ccCode;
    @Column(name = "GL_CODE")
     private String glCode;
    @Column(name = "ORDER_NO")
     private String orderNo;
    @Column(name = "PC_CODE")
     private String pcCode;
    @Column(name = "SP_GL_CODE")
     private String spGlCode;
    @Column(name = "RECONCILE_DATE")
     private Date reconcileDate;
    @Column(name = "DEBIT_AMOUNT")
     private Double debitAmount;
    @Column(name = "CREDIT_AMOUNT")
     private Double creditAmount;
    @Column(name = "DOC_DETAIL_FILE")
     private String docDetailFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACC_VOUCHER_ID", insertable = false, updatable = false)
    @ToString.Exclude
    private Voucher voucher;

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
        if (voucher != null) {
            this.accVoucherId = voucher.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoucherDetails)) return false;
        VoucherDetails that = (VoucherDetails) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
