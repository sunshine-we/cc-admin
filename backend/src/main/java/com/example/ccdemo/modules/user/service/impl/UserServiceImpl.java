package com.example.ccdemo.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ccdemo.common.exception.BusinessException;
import com.example.ccdemo.common.result.ResultCode;
import com.example.ccdemo.modules.role.entity.UserRole;
import com.example.ccdemo.modules.role.mapper.UserRoleMapper;
import com.example.ccdemo.modules.user.entity.User;
import com.example.ccdemo.modules.user.mapper.UserMapper;
import com.example.ccdemo.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author ccDemo
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleMapper userRoleMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public IPage<User> pageUsers(Page<User> page, String username, String nickname) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(username), User::getUsername, username)
               .like(StringUtils.hasText(nickname), User::getNickname, nickname)
               .orderByDesc(User::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void addUser(User user) {
        // 检查用户名唯一性
        Long count = this.count(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (count > 0) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXIST);
        }
        // 默认密码
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode("123456"));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.save(user);
    }

    @Override
    public void updateUser(User user) {
        // 不允许修改用户名
        user.setUsername(null);
        // 不允许通过此方法修改密码
        user.setPassword(null);
        this.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        // 不允许删除自己
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 删除原有角色分配
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId));
        // 重新分配
        if (roleIds != null && !roleIds.isEmpty()) {
            List<UserRole> userRoles = roleIds.stream().map(roleId -> {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            }).collect(Collectors.toList());
            for (UserRole userRole : userRoles) {
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        List<UserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, userId));
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = new User();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(user);
    }
}
