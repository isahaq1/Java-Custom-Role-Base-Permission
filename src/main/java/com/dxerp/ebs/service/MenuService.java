package com.dxerp.ebs.service;
import java.util.List;
import com.dxerp.ebs.entity.Menu;
import com.dxerp.ebs.dto.MenuDTO;
import org.springframework.stereotype.Service;

@Service
public interface  MenuService {
    List<Menu> getAllMenus();
    Menu createMenu(MenuDTO menuDTO);
}
