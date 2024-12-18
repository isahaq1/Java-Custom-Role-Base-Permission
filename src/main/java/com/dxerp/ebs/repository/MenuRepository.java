package com.dxerp.ebs.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.entity.Permission;


import java.util.*;


public interface MenuRepository extends JpaRepository<Menu, Long> {
    // List<Menu> findByPermissionIn(Set<Permission> parent_id);
     List<Menu> findAllByIdIn(List<Long> ids);
     List<Menu> findByParentIdIsNull();
     List<Menu> findByParentId(Long parentId);
}