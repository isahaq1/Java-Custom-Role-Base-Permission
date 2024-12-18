package com.dxerp.ebs.service;
import java.util.List;

import com.dxerp.ebs.entity.Permission;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission createPermission(Permission permission);
}
