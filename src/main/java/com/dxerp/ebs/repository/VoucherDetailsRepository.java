package com.dxerp.ebs.repository;

import com.dxerp.ebs.entity.VoucherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoucherDetailsRepository extends JpaRepository<VoucherDetails, Long> {
    List<VoucherDetails> findByAccVoucherId(Long accVoucherId);
} 