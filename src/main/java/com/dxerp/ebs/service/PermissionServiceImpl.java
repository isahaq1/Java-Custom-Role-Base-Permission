package com.dxerp.ebs.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.dxerp.ebs.entity.Permission;
import com.dxerp.ebs.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }
}
