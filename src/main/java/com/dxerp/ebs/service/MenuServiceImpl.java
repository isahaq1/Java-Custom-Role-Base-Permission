package com.dxerp.ebs.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.dto.MenuDTO;
import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenus() {
         List<Menu> menus = menuRepository.findAll();
        Map<Long, Menu> menuMap = new HashMap<>();
        List<Menu> modules = new ArrayList<>();

        for (Menu menu : menus) {
            if (menu.getParent() == null) {
                // This is a module
                modules.add(menu);
            } else {
                // This is a child menu, add it to its parent's children list
                menu.getParent().getChildren().add(menu);
            }
            menuMap.put(menu.getId(), menu);
        }

        return modules;
    }

    @Override
      @PreAuthorize("hasPermission(null, 'menus/create')")
    public Menu createMenu(MenuDTO menuDTO) {

        
        Menu newMenu = new Menu();
        newMenu.setName(menuDTO.getName());
        newMenu.setUrl(menuDTO.getUrl());
        if (menuDTO.getParentId() != null) {
            Menu parentMenu = menuRepository.findById(menuDTO.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent menu not found with ID: " + menuDTO.getParentId()));
                    newMenu.setParent(parentMenu);
        }
       
        return menuRepository.save(newMenu);
       
    }
}