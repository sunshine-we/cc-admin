package com.example.ccdemo.modules.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ccdemo.modules.menu.entity.Menu;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author ccDemo
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树
     */
    List<Menu> getMenuTree();

    /**
     * 根据当前用户获取菜单树（含权限过滤）
     */
    List<Menu> getCurrentUserMenus(Long userId);

    /**
     * 新增菜单
     */
    void addMenu(Menu menu);

    /**
     * 更新菜单
     */
    void updateMenu(Menu menu);

    /**
     * 删除菜单（含子菜单）
     */
    void deleteMenu(Long id);
}
