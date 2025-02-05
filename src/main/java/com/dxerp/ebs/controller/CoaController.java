package com.dxerp.ebs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dxerp.ebs.dto.CoaDTO;
import com.dxerp.ebs.repository.CoaRepository;
import com.dxerp.ebs.service.CoaService;

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
}