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

import com.dxerp.ebs.dto.LocationDTO;
import com.dxerp.ebs.entity.Location;
import com.dxerp.ebs.service.LocationService;
import com.dxerp.ebs.util.ApiResponse;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
     @Autowired
    private LocationService locationService;
       public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

     @PostMapping("/create")
      @PreAuthorize("hasPermission(null, 'locations/create')")
      public ResponseEntity<ApiResponse<Location>> createLocation(@RequestBody LocationDTO vendorDTO) {
         ApiResponse<Location> response = locationService.createLocation(vendorDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Location>>> getLocationsDropdown() {
     
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(new ApiResponse<>(true, "Locations fetched successfully", locations));
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission(null, 'locations/list')")
    public ResponseEntity<ApiResponse<List<Location>>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(new ApiResponse<>(true, "Location fetched successfully", locations));
    }

        @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getUserById(@PathVariable Long id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        return ResponseEntity.ok(locationDTO);
    }

     @PutMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'locations/edit')")
     public ResponseEntity<ApiResponse<Location>> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        Location updatedLocation = locationService.updateLocation(id, locationDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Location updated successfully", updatedLocation));
    }

      @DeleteMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'locations/delete')")
    public ResponseEntity<ApiResponse<Void>> deleteLocatin(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Location deleted successfully", null));
    } 

    @GetMapping("/warehouseList")
    public List<Location> getwarehouses() {
        return locationService.getwarehouses();
    }

    @GetMapping("/storeList")
    public List<Location> getstores() {
        return locationService.getstores();
    }
}
