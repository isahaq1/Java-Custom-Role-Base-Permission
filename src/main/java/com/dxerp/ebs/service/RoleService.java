package com.dxerp.ebs.service;

import java.util.List;

import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.entity.Role;


public interface RoleService {
    List<Role> getAllRoles();
    Role createRole(String name, List<Long> menuIds);
    List<Menu> getMenusByRole(String roleName);   
}
