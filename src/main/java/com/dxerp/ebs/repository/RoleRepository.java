package com.dxerp.ebs.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.entity.User;



public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
       Role findById(Number id);
    //   Role findById(Long id);
}