package com.dxerp.ebs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxerp.ebs.dto.VendorDTO;
import com.dxerp.ebs.entity.Vendor;
import com.dxerp.ebs.service.VendorService;
import com.dxerp.ebs.util.ApiResponse;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
      @Autowired
    private VendorService vendorService;
       public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

     @PostMapping("/create")
      @PreAuthorize("hasPermission(null, 'vendor/create')")
      public ResponseEntity<ApiResponse<Vendor>> createVendor(@RequestBody VendorDTO vendorDTO) {
         ApiResponse<Vendor> response = vendorService.createVendor(vendorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Vendor>>> getVendorsDropdown() {
     
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(new ApiResponse<>(true, "Vendor fetched successfully", vendors));
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission(null, 'vendors/list')")
    public ResponseEntity<ApiResponse<List<Vendor>>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(new ApiResponse<>(true, "Vendor fetched successfully", vendors));
    }

        @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getUserById(@PathVariable Long id) {
        VendorDTO vendorDTO = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorDTO);
    }

     @PutMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'vendors/edit')")
     public ResponseEntity<ApiResponse<Vendor>> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendorDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vendor updated successfully", updatedVendor));
    }

      @DeleteMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'vendors/delete')")
    public ResponseEntity<ApiResponse<Void>> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vendor deleted successfully", null));
    }
}
