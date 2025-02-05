package com.dxerp.ebs.repository;

import com.dxerp.ebs.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Page<Voucher> findByCompanyId(Long companyId, Pageable pageable);
    Page<Voucher> findByVendorId(Long vendorId, Pageable pageable);
    List<Voucher> findByStatus(Integer status);
    List<Voucher> findByVoucherDateBetween(Date startDate, Date endDate);
    List<Voucher> findByType(String type);
    Long countByStatus(Integer status);
    
    @Query("SELECT SUM(v.totalAmount) FROM Voucher v WHERE v.companyId = :companyId")
    Double sumTotalAmountByCompanyId(Long companyId);
} 