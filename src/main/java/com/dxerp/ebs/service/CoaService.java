package com.dxerp.ebs.service;
import java.util.List;

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
}