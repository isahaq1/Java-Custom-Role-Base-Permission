package com.dxerp.ebs.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dxerp.ebs.dto.CoaDTO;
import com.dxerp.ebs.repository.CoaRepository;
import com.dxerp.ebs.service.CoaService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api/coa")
public class CoaController {

    @Autowired
    private CoaService coaService;

    @PostMapping("/create")
    public CoaRepository.CoaResponse createCoa(@RequestBody CoaDTO coaDto) {
        return coaService.createCoaHead(
            coaDto.getCoaName(), coaDto.getCoaType(), coaDto.getIsGroupHead(),
            coaDto.getKeyWord(), coaDto.getParentId(), coaDto.getSortBy(),
            coaDto.getGroupName(), coaDto.getGroupCode(), coaDto.getCompanyCode(),
            0, coaDto.getGcBk()
        );
    }

@GetMapping("/list")
public List<CoaDTO> listCoa() {
    return coaService.getAllCoaHeads();
}

@GetMapping("/payable-heads")
public ResponseEntity<List<CoaDTO>> getPayableHeads() {
    List<CoaDTO> payableHeads = coaService.getCoaByType("Liability");
    return ResponseEntity.ok(payableHeads);
}

@GetMapping("/payment-methods")
public ResponseEntity<List<CoaDTO>> getCoaByTypeAndKeyword() {
    List<CoaDTO> payableHeads = coaService.getCoaByTypeAndKeyword("Asset","paymentMethod");
    return ResponseEntity.ok(payableHeads);
}

@GetMapping("/coa-report/{format}")
public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
    System.out.println("COA HEADS: " + format);
    return coaService.exportReport(format);
}

}