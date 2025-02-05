package com.dxerp.ebs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxerp.ebs.entity.PurchaseDetails;

public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Long> {
}