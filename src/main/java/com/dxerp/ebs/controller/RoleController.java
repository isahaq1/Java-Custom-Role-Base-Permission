package com.dxerp.ebs.controller;
import org.springframework.web.bind.annotation.*;

import com.dxerp.ebs.dto.RoleRequest;
import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.entity.Permission;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.repository.PermissionRepository;
import com.dxerp.ebs.repository.RoleRepository;

import com.dxerp.ebs.util.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.dxerp.ebs.service.RoleService;



@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role createRole(@RequestBody RoleRequest roleRequest) {
  
        return roleService.createRole(roleRequest.getName(), roleRequest.getMenuIds());
    }

    @GetMapping("/{roleName}/menus")
    public List<Menu> getMenusByRole(@PathVariable String roleName) {
        return roleService.getMenusByRole(roleName);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}
