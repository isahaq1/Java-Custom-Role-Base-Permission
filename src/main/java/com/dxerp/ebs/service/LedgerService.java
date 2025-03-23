package com.dxerp.ebs.service;

import com.dxerp.ebs.dto.LedgerDTO;
import com.dxerp.ebs.entity.Ledger;
import com.dxerp.ebs.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public LedgerDTO createLedger(LedgerDTO ledgerDTO) {
        Ledger ledger = new Ledger();
        ledger.setVoucherNo(ledgerDTO.getVoucherNo());
        ledger.setVoucherDate(ledgerDTO.getVoucherDate());
        ledger.setCoaId(ledgerDTO.getCoaId());
        ledger.setCoaCode(ledgerDTO.getCoaCode());
        ledger.setDebitAmount(ledgerDTO.getDebitAmount());
        ledger.setCreditAmount(ledgerDTO.getCreditAmount());
        ledger.setRefCoaCode(ledgerDTO.getRefCoaCode());
        ledger.setRemarks(ledgerDTO.getRemarks());
        ledger.setCreatedBy(ledgerDTO.getCreatedBy());

        Ledger savedLedger = ledgerRepository.save(ledger);
        ledgerDTO.setId(savedLedger.getId());
        return ledgerDTO;
    }

    public List<LedgerDTO> getAllLedgers() {
        return ledgerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private LedgerDTO toDTO(Ledger ledger) {
        LedgerDTO dto = new LedgerDTO();
        dto.setId(ledger.getId());
        dto.setVoucherNo(ledger.getVoucherNo());
        dto.setVoucherDate(ledger.getVoucherDate());
        dto.setCoaId(ledger.getCoaId());
        dto.setCoaCode(ledger.getCoaCode());
        dto.setDebitAmount(ledger.getDebitAmount());
        dto.setCreditAmount(ledger.getCreditAmount());
        dto.setRefCoaCode(ledger.getRefCoaCode());
        dto.setRemarks(ledger.getRemarks());
        dto.setCreatedBy(ledger.getCreatedBy());
        return dto;
    }
}
