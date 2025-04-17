package com.dxerp.ebs.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.dxerp.ebs.dto.CoaDTO;
import com.dxerp.ebs.repository.CoaRepository;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

    public String exportReport(String format) throws FileNotFoundException, JRException {
         List<CoaDTO> coaHeads = coaRepository.findAll();
       
         String path = "C:\\Users\\Ishaq\\Desktop\\Reports";
         File file = ResourceUtils.getFile("classpath:coareport.jrxml");
         JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
         JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(coaHeads);
         Map<String, Object> parameters = new HashMap<>();
       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
         if (format.equalsIgnoreCase("html")) {
             JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\coaHeads.html");
         } else if (format.equalsIgnoreCase("pdf")) {
            // JasperExportManager.exportReportToPdfFile(jasperPrint, "coaHeads.pdf");
              JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\coaHeads.pdf");
         }
         // Add more formats as needed
           
        return "Report generated successfully!";
    }
}