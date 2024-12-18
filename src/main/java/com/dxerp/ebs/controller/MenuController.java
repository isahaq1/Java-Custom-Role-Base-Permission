package com.dxerp.ebs.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.dxerp.ebs.repository.MenuRepository;
import com.dxerp.ebs.entity.Menu;

import com.dxerp.ebs.service.MenuService;
import com.dxerp.ebs.dto.MenuDTO;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;
    private MenuRepository menuRepository;

      public MenuController(MenuService menuService, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping
    public Menu createMenu(@RequestBody MenuDTO menuDTO) {
        return menuService.createMenu(menuDTO);
    }

     @GetMapping("/parents")
    public ResponseEntity<List<Menu>> getParentMenus() {
        List<Menu> parentMenus = menuRepository.findByParentIdIsNull(); // Get only top-level menus
        return ResponseEntity.ok(parentMenus);
    }

    @GetMapping("/children/{parentId}")
    public List<Menu> getChildMenus(Long parentId) {
        System.out.print("Request parent "+parentId);
        return menuRepository.findByParentId(parentId);
    }
}