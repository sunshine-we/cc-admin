package com.example.ccdemo.modules.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ccdemo.modules.user.entity.User;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author ccDemo
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户列表
     */
    IPage<User> pageUsers(Page<User> page, String username, String nickname);

    /**
     * 新增用户
     */
    void addUser(User user);

    /**
     * 更新用户
     */
    void updateUser(User user);

    /**
     * 删除用户（逻辑删除）
     */
    void deleteUser(Long id);

    /**
     * 分配角色
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户的角色 ID 列表
     */
    List<Long> getUserRoleIds(Long userId);

    /**
     * 重置密码
     */
    void resetPassword(Long userId, String newPassword);
}
