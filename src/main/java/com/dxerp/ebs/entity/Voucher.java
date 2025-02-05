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
@Table(name = "VOUCHERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE", length = 50)
    private String type;

    @Column(name = "VOUCHER_NO", length = 50)
    private String voucherNo;

    @Column(name = "APPROVE_BY_ID")
    private Long approveById;

    @Column(name = "APPROVE_DATE")
    @Temporal(TemporalType.DATE)
    private Date approveDate;

    @Column(name = "VOUCHER_DATE")
    @Temporal(TemporalType.DATE)
    private Date voucherDate;

    @Column(name = "CHEQUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date chequeDate;

    @Column(name = "CHEQUE_NO", length = 50)
    private String chequeNo;

    @Column(name = "NARRATION", length = 500)
    private String narration;

    @Column(name = "VOUCHER_STATUS")
    private Integer voucherStatus;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "CREATE_BY_ID")
    private Long createById;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "COMPANY_ID")
    private Long companyId;

    @Column(name = "COMPANY_CODE", length = 50)
    private String companyCode;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "VOUCHER_NUMBER", length = 255)
    private String voucherNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDOR_ID")
    private Vendor vendor;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherDetails> voucherDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        createDate = new Date();
        if (status == null) {
            status = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addVoucherDetail(VoucherDetails detail) {
        if (detail != null) {
            voucherDetails.add(detail);
            detail.setVoucher(this);
        }
    }

    public void removeVoucherDetail(VoucherDetails detail) {
        if (detail != null) {
            voucherDetails.remove(detail);
        }
        detail.setVoucher(null);
    }
} 