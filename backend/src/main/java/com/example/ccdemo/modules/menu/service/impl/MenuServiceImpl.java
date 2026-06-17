package com.example.ccdemo.modules.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ccdemo.modules.menu.entity.Menu;
import com.example.ccdemo.modules.menu.entity.RoleMenu;
import com.example.ccdemo.modules.menu.mapper.MenuMapper;
import com.example.ccdemo.modules.menu.mapper.RoleMenuMapper;
import com.example.ccdemo.modules.menu.service.MenuService;
import com.example.ccdemo.modules.role.entity.UserRole;
import com.example.ccdemo.modules.role.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 *
 * @author ccDemo
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final UserRoleMapper userRoleMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> allMenus = this.list(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getStatus, 1)
                .orderByAsc(Menu::getSort));
        return buildTree(allMenus, 0L);
    }

    @Override
    public List<Menu> getCurrentUserMenus(Long userId) {
        // 查询用户角色
        List<UserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 查询角色对应的菜单
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(
                new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIds));
        Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());

        if (menuIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 查询菜单（仅菜单和目录类型，不返回按钮）
        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>()
                .in(Menu::getId, menuIds)
                .in(Menu::getMenuType, Arrays.asList("catalog", "menu"))
                .eq(Menu::getStatus, 1)
                .orderByAsc(Menu::getSort));

        return buildTree(menus, 0L);
    }

    @Override
    public void addMenu(Menu menu) {
        this.save(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        this.updateById(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        // 递归删除子菜单
        List<Menu> children = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id));
        for (Menu child : children) {
            deleteMenu(child.getId());
        }
        this.removeById(id);
    }

    /**
     * 构建菜单树
     */
    private List<Menu> buildTree(List<Menu> allMenus, Long parentId) {
        return allMenus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(buildTree(allMenus, menu.getId()))) // 这里用 peek 而不是 map，但需要 Menu 中有 children 字段
                .collect(Collectors.toList());
    }
}
