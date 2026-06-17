package com.example.ccdemo.modules.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ccdemo.modules.role.entity.Role;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author ccDemo
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页查询角色
     */
    IPage<Role> pageRoles(Page<Role> page, String roleName);

    /**
     * 新增角色
     */
    void addRole(Role role);

    /**
     * 更新角色
     */
    void updateRole(Role role);

    /**
     * 删除角色
     */
    void deleteRole(Long id);

    /**
     * 分配菜单权限
     */
    void assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 获取角色的菜单 ID 列表
     */
    List<Long> getRoleMenuIds(Long roleId);
}
