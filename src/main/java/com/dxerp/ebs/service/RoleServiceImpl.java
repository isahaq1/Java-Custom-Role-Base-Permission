package com.dxerp.ebs.service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.entity.Role;
import com.dxerp.ebs.repository.MenuRepository;
import com.dxerp.ebs.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

     @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

     @PreAuthorize("hasPermission(null, 'roles/create')")
    public Role createRole(String roleName, List<Long> menuIds) {
        Role role = new Role();
        role.setName(roleName);

        Set<Menu> menus = new HashSet<>(menuRepository.findAllById(menuIds));
        role.setMenus(menus);

        return roleRepository.save(role);
    }

    public List<Menu> getMenusByRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return new ArrayList<>(role.getMenus());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}