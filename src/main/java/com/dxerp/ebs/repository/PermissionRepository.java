package com.dxerp.ebs.repository;
import com.dxerp.ebs.entity.Permission;
import com.dxerp.ebs.entity.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    // Optional<Permission> findByName(String name);
    Permission findAllById(Number id);
}
