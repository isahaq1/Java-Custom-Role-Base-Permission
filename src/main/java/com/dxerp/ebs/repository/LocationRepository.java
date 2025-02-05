package com.dxerp.ebs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.dxerp.ebs.entity.Location;


public interface LocationRepository extends JpaRepository<Location, Long> {

    
    List<Location> findByLocationTypeAndStatus(Integer locationType, boolean status);
    
}