package com.dxerp.ebs.controller;

import com.dxerp.ebs.dto.VoucherDTO;
import com.dxerp.ebs.entity.Voucher;
import com.dxerp.ebs.entity.VoucherDetails;
import com.dxerp.ebs.service.VoucherService;
import com.dxerp.ebs.util.ApiResponse;
import com.dxerp.ebs.dto.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
    
    @Autowired
    private VoucherService voucherService;

    @PostMapping("/create")
    @PreAuthorize("hasPermission(null, 'vouchers/create')")
    public ResponseEntity<ApiResponse<Voucher>> createVoucher(@RequestBody VoucherDTO voucherDTO) {
        return ResponseEntity.ok(voucherService.createVoucher(voucherDTO));
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<PaginatedResponse<Voucher>>> getAllVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherService.getAllVouchers(pageable);
        PaginatedResponse<Voucher> response = new PaginatedResponse<>(voucherPage.getContent(), voucherPage);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vouchers fetched successfully", response));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<PaginatedResponse<Voucher>>> getVouchersByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherService.getVouchersByCompany(companyId, pageable);
        PaginatedResponse<Voucher> response = new PaginatedResponse<>(voucherPage.getContent(), voucherPage);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company vouchers fetched successfully", response));
    }

    @GetMapping("/vendor/{vendorId}")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<PaginatedResponse<Voucher>>> getVouchersByVendor(
            @PathVariable Long vendorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherService.getVouchersByVendor(vendorId, pageable);
        PaginatedResponse<Voucher> response = new PaginatedResponse<>(voucherPage.getContent(), voucherPage);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vendor vouchers fetched successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(null, 'vouchers/view')")
    public ResponseEntity<ApiResponse<VoucherDTO>> getVoucherById(@PathVariable Long id) {
        VoucherDTO voucherDTO = voucherService.getVoucherById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher fetched successfully", voucherDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(null, 'vouchers/edit')")
    public ResponseEntity<ApiResponse<Voucher>> updateVoucher(
            @PathVariable Long id,
            @RequestBody VoucherDTO voucherDTO) {
        Voucher updatedVoucher = voucherService.updateVoucher(id, voucherDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher updated successfully", updatedVoucher));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(null, 'vouchers/delete')")
    public ResponseEntity<ApiResponse<Void>> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voucher deleted successfully", null));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<List<Voucher>>> getVouchersByStatus(@PathVariable Integer status) {
        List<Voucher> vouchers = voucherService.getVouchersByStatus(status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status vouchers fetched successfully", vouchers));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<List<Voucher>>> getVouchersByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Voucher> vouchers = voucherService.getVouchersByDateRange(startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(true, "Date range vouchers fetched successfully", vouchers));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasPermission(null, 'vouchers/approve')")
    public ResponseEntity<ApiResponse<Voucher>> approveVoucher(
            @PathVariable Long id,
            @RequestParam Long approveById) {
        return ResponseEntity.ok(voucherService.approveVoucher(id, approveById));
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<List<Voucher>>> getVouchersByType(@PathVariable String type) {
        List<Voucher> vouchers = voucherService.getVouchersByType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Type vouchers fetched successfully", vouchers));
    }

    @GetMapping("/company/{companyId}/total")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<Double>> getTotalAmountByCompany(@PathVariable Long companyId) {
        Double totalAmount = voucherService.getTotalAmountByCompany(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Company total amount fetched successfully", totalAmount));
    }

    @GetMapping("/status/{status}/count")
    @PreAuthorize("hasPermission(null, 'vouchers/list')")
    public ResponseEntity<ApiResponse<Long>> getVoucherCountByStatus(@PathVariable Integer status) {
        Long count = voucherService.getVoucherCountByStatus(status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status count fetched successfully", count));
    }

   
}