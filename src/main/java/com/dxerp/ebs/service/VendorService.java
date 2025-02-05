package com.dxerp.ebs.service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.dto.VendorDTO;
import com.dxerp.ebs.entity.Vendor;
import com.dxerp.ebs.repository.CoaRepository.CoaResponse;
import com.dxerp.ebs.repository.VendorRepository;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class VendorService {
      @Autowired
    private VendorRepository vendorRepository;
    private JdbcTemplate jdbcTemplate;
    public VendorService(VendorRepository vendorRepository, JdbcTemplate jdbcTemplate) {
        this.vendorRepository = vendorRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApiResponse<Vendor> createVendor(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setStatus(true);
        vendorRepository.save(vendor);
        jdbcTemplate.execute((ConnectionCallback<CoaResponse>) connection -> {
            CallableStatement callableStatement = connection.prepareCall(
                "{CALL CREATE_COA_HEAD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"
            );

            callableStatement.setString(1, vendorDTO.getName());
            callableStatement.setString(2, "Liability");
            callableStatement.setInt(3, 0);
            callableStatement.setString(4, "Vendor");
            callableStatement.setLong(5, 19);
            callableStatement.setInt(6, 1);
            callableStatement.setString(7, "Vendore");
            callableStatement.setString(8, "vendor-01");
            callableStatement.setString(9, "Vendor-001");
            callableStatement.setInt(10, 0);
            callableStatement.setString(11, "vendoretest");
            
            callableStatement.registerOutParameter(12, Types.NUMERIC); // o_coa_id
            callableStatement.registerOutParameter(13, Types.VARCHAR); // o_generated_code

            callableStatement.execute();
            Long generatedId = callableStatement.getLong(12);
            String generatedCode = callableStatement.getString(13);

            return new CoaResponse(generatedId, generatedCode);
        });

        return new ApiResponse<>(true, "vendor Successfully Created", vendor);
    }

    public List<Vendor> getAllVendors() {
       return  vendorRepository.findAll();
        
    }

    @PreAuthorize("hasPermission(null, 'vendor/delete')")
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("vendor not found"));
        vendorRepository.delete(vendor);
    }

    public Vendor updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("vendor not found"));
              
        vendor.setName(vendorDTO.getName());
        vendor.setStatus(vendorDTO.getStatus());
        return vendorRepository.save(vendor);
    }

    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vendor not found"));
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(vendor.getId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setStatus(vendor.getStatus());
        return vendorDTO;
        
    }





}
