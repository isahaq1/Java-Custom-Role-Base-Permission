package com.dxerp.ebs.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxerp.ebs.dto.CoaDTO;
import com.dxerp.ebs.repository.CoaRepository;

@Service
public class CoaService {

    @Autowired
    private CoaRepository coaRepository;

    public CoaRepository.CoaResponse createCoaHead(String coaName, String coaType, int isGroupHead, String keyWord, 
                                                   Long parentId, int sortBy, String groupName, String groupCode, 
                                                   String companyCode, int isSpecialGl, String gcBk) {
        return coaRepository.createCoaHead(coaName, coaType, isGroupHead, keyWord, parentId, sortBy, 
                                           groupName, groupCode, companyCode, isSpecialGl, gcBk);
    }
       public List<CoaDTO> getAllCoaHeads() {

        // Implementation here

        return coaRepository.findAll();

    }
    public List<CoaDTO> getCoaByType(String coaType) {
        
        return coaRepository.findAllByCoaType(coaType)
                .stream()
                .map(coa -> {
                    CoaDTO dto = new CoaDTO();
                    dto.setId(coa.getId());
                    dto.setCoaName(coa.getCoaName());
                    dto.setCoaType(coa.getCoaType());
                    dto.setGroupCode(coa.getGroupCode());
                    dto.setGroupName(coa.getGroupName());
                    // Set other necessary fields
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CoaDTO> getCoaByTypeAndKeyword(String coaType,String Keyword) {
        
        return coaRepository.findAllByCoaTypeAndKeyword(coaType,Keyword)
                .stream()
                .map(coa -> {
                    CoaDTO dto = new CoaDTO();
                    dto.setId(coa.getId());
                    dto.setCoaName(coa.getCoaName());
                    dto.setCoaType(coa.getCoaType());
                    dto.setGroupCode(coa.getGroupCode());
                    dto.setGroupName(coa.getGroupName());
                    // Set other necessary fields
                    return dto;
                })
                .collect(Collectors.toList());
    }
}