package com.example.ccdemo.common.config;

import com.example.ccdemo.modules.role.entity.Role;
import com.example.ccdemo.modules.role.mapper.RoleMapper;
import com.example.ccdemo.modules.role.mapper.UserRoleMapper;
import com.example.ccdemo.modules.role.entity.UserRole;
import com.example.ccdemo.modules.user.entity.User;
import com.example.ccdemo.modules.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器 —— 首次启动时自动创建管理员账号
 *
 * @author ccDemo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 初始化管理员角色
        if (roleMapper.selectCount(null) == 0) {
            Role adminRole = new Role();
            adminRole.setId(1L);
            adminRole.setRoleName("超级管理员");
            adminRole.setRoleCode("admin");
            adminRole.setDescription("拥有所有权限");
            adminRole.setStatus(1);
            roleMapper.insert(adminRole);
            log.info("已初始化管理员角色");

            Role userRole = new Role();
            userRole.setId(2L);
            userRole.setRoleName("普通用户");
            userRole.setRoleCode("user");
            userRole.setDescription("基础用户权限");
            userRole.setStatus(1);
            roleMapper.insert(userRole);
            log.info("已初始化普通用户角色");
        } else {
            log.info("角色数据已存在，跳过初始化");
        }

        // 初始化管理员用户
        if (userMapper.selectCount(null) == 0) {
            User admin = new User();
            admin.setId(1L);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setNickname("超级管理员");
            admin.setEmail("admin@ccdemo.com");
            admin.setStatus(1);
            userMapper.insert(admin);
            log.info("已初始化管理员用户（用户名: admin，密码: 123456）");

            // 分配管理员角色
            UserRole userRoleMapping = new UserRole();
            userRoleMapping.setUserId(1L);
            userRoleMapping.setRoleId(1L);
            userRoleMapper.insert(userRoleMapping);
            log.info("已为管理员分配超级管理员角色");
        } else {
            log.info("用户数据已存在，跳过初始化");
        }
    }
}
