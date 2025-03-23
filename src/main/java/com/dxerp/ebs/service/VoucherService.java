package com.dxerp.ebs.service;

import com.dxerp.ebs.entity.Voucher;
import com.dxerp.ebs.entity.VoucherDetails;
import com.dxerp.ebs.dto.VoucherDTO;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

public interface VoucherService {
    // Create new voucher
    ApiResponse<Voucher> createVoucher(VoucherDTO voucherDTO);
    
    // Get all vouchers with pagination
    Page<Voucher> getAllVouchers(Pageable pageable);
    Page<VoucherDTO> getVoucherList(Pageable pageable);
    
    
    // Get vouchers by company with pagination
    Page<Voucher> getVouchersByCompany(Long companyId, Pageable pageable);
    
    // Get vouchers by vendor with pagination
    Page<Voucher> getVouchersByVendor(Long vendorId, Pageable pageable);
    
    // Get single voucher by ID
    VoucherDTO getVoucherById(Long id);
    
    // Update existing voucher
    Voucher updateVoucher(Long id, VoucherDTO voucherDTO);
    
    // Delete voucher
    void deleteVoucher(Long id);
    
    // Get vouchers by status
    List<Voucher> getVouchersByStatus(Integer status);
    
    // Get vouchers by date range
    List<Voucher> getVouchersByDateRange(Date startDate, Date endDate);
    
    // Approve voucher
    ApiResponse<Voucher> approveVoucher(Long id, Long approveById);
    
    // Get vouchers by type
    List<Voucher> getVouchersByType(String type);
    
    // Get total amount by company
    Double getTotalAmountByCompany(Long companyId);
    // Get voucher count by status
    Long getVoucherCountByStatus(Integer status);
    
    // Get voucher details by ID
    Voucher getVoucherDetailsById(Long id);

 
}
