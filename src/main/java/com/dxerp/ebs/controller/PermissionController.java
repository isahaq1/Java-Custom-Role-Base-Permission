package com.dxerp.ebs.controller;
import org.springframework.web.bind.annotation.*;

import com.dxerp.ebs.entity.Permission;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.repository.PermissionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.dxerp.ebs.service.PermissionService;


@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @PostMapping
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }
}