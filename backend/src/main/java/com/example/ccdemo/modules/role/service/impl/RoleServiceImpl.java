package com.example.ccdemo.modules.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ccdemo.common.exception.BusinessException;
import com.example.ccdemo.common.result.ResultCode;
import com.example.ccdemo.modules.menu.entity.RoleMenu;
import com.example.ccdemo.modules.menu.mapper.RoleMenuMapper;
import com.example.ccdemo.modules.role.entity.Role;
import com.example.ccdemo.modules.role.mapper.RoleMapper;
import com.example.ccdemo.modules.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 *
 * @author ccDemo
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuMapper roleMenuMapper;

    @Override
    public IPage<Role> pageRoles(Page<Role> page, String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName)
               .orderByDesc(Role::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void addRole(Role role) {
        Long count = this.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleCode, role.getRoleCode()));
        if (count > 0) {
            throw new BusinessException(ResultCode.DATA_EXIST.getCode(), "角色编码已存在");
        }
        this.save(role);
    }

    @Override
    public void updateRole(Role role) {
        // 不允许修改角色编码
        role.setRoleCode(null);
        this.updateById(role);
    }

    @Override
    public void deleteRole(Long id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(Long roleId, List<Long> menuIds) {
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId));
        if (menuIds != null && !menuIds.isEmpty()) {
            List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(roleId);
                rm.setMenuId(menuId);
                return rm;
            }).collect(Collectors.toList());
            for (RoleMenu roleMenu : roleMenus) {
                roleMenuMapper.insert(roleMenu);
            }
        }
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(
                new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, roleId));
        return roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
