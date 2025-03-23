package com.dxerp.ebs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "LEDGER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VOUCHER_NO", length = 30, nullable = false)
    private String voucherNo;


    @Column(name = "VOUCHER_DATE")
    @Temporal(TemporalType.DATE)
    private Date voucherDate;

    @Column(name = "COA_ID", nullable = false)
    private Long coaId;
    @Column(name = "COA_CODE", length = 30, nullable = true)
    private String coaCode;

    @Column(name = "DEBIT_AMOUNT",  nullable = true)
    private Double debitAmount;

    @Column(name = "CREDIT_AMOUNT",  nullable = true)
    private Double creditAmount;

    @Column(name = "REF_COA_CODE", length = 30, nullable = true)
    private String refCoaCode;

    @Column(name = "REMARKS", length = 4000, nullable = true)
    private String remarks;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    
}
