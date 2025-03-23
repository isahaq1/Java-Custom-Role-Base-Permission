package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.LedgerDTO;
import com.dxerp.ebs.dto.VoucherDTO;
import com.dxerp.ebs.dto.VoucherDetailsDTO;
import com.dxerp.ebs.entity.Voucher;
import com.dxerp.ebs.entity.VoucherDetails;
import com.dxerp.ebs.entity.Vendor;
import com.dxerp.ebs.repository.VoucherRepository;
import com.dxerp.ebs.repository.VendorRepository;
import com.dxerp.ebs.repository.VoucherDetailsRepository;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherDetailsRepository voucherDetailsRepository;


    @Autowired
    private LedgerService ledgerService;

    @Override
    public Voucher getVoucherDetailsById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        return voucher;
    }

    @Override
    public ApiResponse<Voucher> createVoucher(VoucherDTO voucherDTO) {
          try {
          
            Voucher voucher = new Voucher();
            mapDtoToEntity(voucherDTO, voucher);
            voucher.setCreatedAt(LocalDateTime.now());
            voucher.setStatus(1);
            voucher.setVoucherStatus(1); // Set initial status

   

            // Save voucher first to get the ID
            Voucher savedVoucher = voucherRepository.save(voucher);

            // Create voucher details with the saved voucher ID
            if (voucherDTO.getVoucherDetails() != null && !voucherDTO.getVoucherDetails().isEmpty()) {
                List<VoucherDetails> detailsList = new ArrayList<>();
                Long slNo = 1L;
                Double totalDebit = 0.0;
                Double totalCredit = 0.0;

                for (VoucherDetailsDTO detailDTO : voucherDTO.getVoucherDetails()) {
                    VoucherDetails detail = new VoucherDetails();
                    mapDetailsDtoToEntity(detailDTO, detail);

                    // Set the voucher relationship and serial number
                    detail.setAccVoucherId(savedVoucher.getId());
                    detail.setSlNo(slNo++);

                    // Calculate totals
                    if (detail.getDebitAmount() != null) {
                        totalDebit += detail.getDebitAmount();
                    }
                    if (detail.getCreditAmount() != null) {
                        totalCredit += detail.getCreditAmount();
                    }

                    detailsList.add(detail);

                    // Create ledger entry for each voucher detail
                    LedgerDTO ledgerDTO = new LedgerDTO();
                    ledgerDTO.setVoucherNo(savedVoucher.getVoucherNo());
                    ledgerDTO.setVoucherDate(savedVoucher.getVoucherDate());
                    ledgerDTO.setCoaId(detail.getAccCoaId());
                    ledgerDTO.setCoaCode(detail.getGlCode());
                    ledgerDTO.setDebitAmount(detail.getDebitAmount());
                    ledgerDTO.setCreditAmount(detail.getCreditAmount());
                    ledgerDTO.setRefCoaCode(detail.getRefAccCoaId() != null ? detail.getRefAccCoaId().toString() : null);
                    ledgerDTO.setRemarks(detail.getLedgerText());
                    ledgerDTO.setCreatedBy(voucher.getCreateById());

                    ledgerService.createLedger(ledgerDTO);
                }

                // Save all details at once
                voucherDetailsRepository.saveAll(detailsList);

                // Update voucher with totals
                savedVoucher.setTotalAmount(totalDebit); // or totalCredit, they should be equal
                savedVoucher = voucherRepository.save(savedVoucher);
            }

            return new ApiResponse<>(true, "Voucher created successfully", savedVoucher);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error creating voucher: " + e.getMessage(), null);
        }
    }

    private void mapDetailsDtoToEntity(VoucherDetailsDTO dto, VoucherDetails entity) {
        entity.setAccCoaId(dto.getAccCoaId());
        entity.setAccCostCenterId(dto.getAccCostCenterId());
        entity.setCreditAmountBk(dto.getCreditAmountBk());
        entity.setDebitAmountBk(dto.getDebitAmountBk());
        entity.setMasterKey(dto.getMasterKey());
        entity.setMasterRef(dto.getMasterRef());
        entity.setRefAccCoaId(dto.getRefAccCoaId());
        entity.setLedgerText(dto.getLedgerText());
        entity.setLineText(dto.getLineText());
        entity.setAccFlexId(dto.getAccFlexId());
        entity.setAccProfitCenterId(dto.getAccProfitCenterId());
        entity.setBpId(dto.getBpId());
        entity.setBpCode(dto.getBpCode());
        entity.setCcCode(dto.getCcCode());
        entity.setGlCode(dto.getGlCode());
        entity.setOrderNo(dto.getOrderNo());
        entity.setPcCode(dto.getPcCode());
        entity.setSpGlCode(dto.getSpGlCode());
        entity.setReconcileDate(dto.getReconcileDate());
        entity.setDebitAmount(dto.getDebitAmount());
        entity.setCreditAmount(dto.getCreditAmount());
        entity.setDocDetailFile(dto.getDocDetailFile());
    }

    @Override
    public Page<Voucher> getAllVouchers(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    @Override
    public Page<VoucherDTO> getVoucherList(Pageable pageable) {
        return voucherRepository.findVouchers(pageable);
    }

    @Override
    public Page<Voucher> getVouchersByCompany(Long companyId, Pageable pageable) {
        return voucherRepository.findByCompanyId(companyId, pageable);
    }

 

    @Override
    public VoucherDTO getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        return mapEntityToDto(voucher);
    }

    @Override
    public Voucher updateVoucher(Long id, VoucherDTO voucherDTO) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));

        mapDtoToEntity(voucherDTO, existingVoucher);
        existingVoucher.setUpdatedAt(LocalDateTime.now());
        return voucherRepository.save(existingVoucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        voucherRepository.delete(voucher);
    }

    @Override
    public List<Voucher> getVouchersByStatus(Integer status) {
        return voucherRepository.findByStatus(status);
    }

    @Override
    public List<Voucher> getVouchersByDateRange(Date startDate, Date endDate) {
        return voucherRepository.findByVoucherDateBetween(startDate, endDate);
    }

    @Override
    public ApiResponse<Voucher> approveVoucher(Long id, Long approveById) {
        try {
            Voucher voucher = voucherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));

            voucher.setApproveById(approveById);
            voucher.setApproveDate(new Date());
            voucher.setVoucherStatus(2); // Approved status
            voucher.setUpdatedAt(LocalDateTime.now());

            Voucher approvedVoucher = voucherRepository.save(voucher);
            return new ApiResponse<>(true, "Voucher approved successfully", approvedVoucher);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error approving voucher: " + e.getMessage(), null);
        }
    }

    @Override
    public List<Voucher> getVouchersByType(String type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public Double getTotalAmountByCompany(Long companyId) {
        return voucherRepository.sumTotalAmountByCompanyId(companyId);
    }

    @Override
    public Long getVoucherCountByStatus(Integer status) {
        return voucherRepository.countByStatus(status);
    }

    // Helper methods for DTO-Entity mapping
    private void mapDtoToEntity(VoucherDTO dto, Voucher entity) {
        entity.setType(dto.getType());
        entity.setVoucherNo(dto.getVoucherNo());
        entity.setVoucherDate(dto.getVoucherDate());
        entity.setChequeDate(dto.getChequeDate());
        entity.setChequeNo(dto.getChequeNo());
        entity.setNarration(dto.getNarration());
        entity.setVoucherStatus(dto.getVoucherStatus());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setCreateById(dto.getCreateById());
        entity.setCompanyId(dto.getCompanyId());
        entity.setCompanyCode(dto.getCompanyCode());
        entity.setAmount(dto.getAmount());
        entity.setDescription(dto.getDescription());
        entity.setVoucherNumber(dto.getVoucherNumber());

    }

    private VoucherDTO mapEntityToDto(Voucher entity) {
        VoucherDTO dto = new VoucherDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setVoucherNo(entity.getVoucherNo());
        dto.setApproveById(entity.getApproveById());
        dto.setApproveDate(entity.getApproveDate());
        dto.setVoucherDate(entity.getVoucherDate());
        dto.setChequeDate(entity.getChequeDate());
        dto.setChequeNo(entity.getChequeNo());
        dto.setNarration(entity.getNarration());
        dto.setVoucherStatus(entity.getVoucherStatus());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setCreateById(entity.getCreateById());
        dto.setCreateDate(entity.getCreateDate());
        dto.setCompanyId(entity.getCompanyId());
        dto.setCompanyCode(entity.getCompanyCode());
        dto.setAmount(entity.getAmount());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setVoucherNumber(entity.getVoucherNumber());

        return dto;
    }
}