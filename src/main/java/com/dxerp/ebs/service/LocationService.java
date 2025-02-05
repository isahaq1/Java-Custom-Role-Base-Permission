package com.dxerp.ebs.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.dto.LocationDTO;

import com.dxerp.ebs.entity.Location;
import com.dxerp.ebs.repository.LocationRepository;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
   @Autowired
    private LocationRepository locationRepository;

    public ApiResponse<Location> createLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setContact(locationDTO.getContact());
        location.setLocationType(locationDTO.getLocationType());
        location.setStatus(true);
        locationRepository.save(location);

        return new ApiResponse<>(true, "location Successfully Created", location);
    }

    public List<Location> getAllLocations() {
       return  locationRepository.findAll();
        
    }

    @PreAuthorize("hasPermission(null, 'locations/delete')")
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("location not found"));
                locationRepository.delete(location);
    }

    public Location updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("location not found"));
        
        location.setName(locationDTO.getName());
        location.setAddress(locationDTO.getAddress());
        location.setContact(locationDTO.getContact());
        location.setLocationType(locationDTO.getLocationType());
        location.setStatus(locationDTO.getStatus()); 
        return locationRepository.save(location);
    }

    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setName(location.getName());
        locationDTO.setAddress(location.getAddress());
        locationDTO.setContact(location.getContact());
        locationDTO.setLocationType(location.getLocationType());
        locationDTO.setStatus(location.getStatus());
        return locationDTO;
        
    }  

    public List<Location> getwarehouses() {
        return locationRepository.findByLocationTypeAndStatus(0, true);
    }

    public List<Location> getstores() {
        return locationRepository.findByLocationTypeAndStatus(1, true);
    }
}
