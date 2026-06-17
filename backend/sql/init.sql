-- ============================================
-- ccDemo 数据库初始化脚本
-- ============================================

-- 创建数据库（如已存在则忽略）
CREATE DATABASE IF NOT EXISTS ccdemo
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE ccdemo;

-- ==================== 用户表 ====================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`          BIGINT        NOT NULL COMMENT '主键ID',
    `username`    VARCHAR(50)   NOT NULL COMMENT '用户名',
    `password`    VARCHAR(255)  NOT NULL COMMENT '密码（BCrypt 加密）',
    `nickname`    VARCHAR(50)   DEFAULT NULL COMMENT '昵称',
    `email`       VARCHAR(100)  DEFAULT NULL COMMENT '邮箱',
    `phone`       VARCHAR(20)   DEFAULT NULL COMMENT '手机号',
    `avatar`      VARCHAR(255)  DEFAULT NULL COMMENT '头像URL',
    `status`      TINYINT       NOT NULL DEFAULT 1 COMMENT '状态（0=禁用，1=正常）',
    `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除（0=正常，1=已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ==================== 角色表 ====================
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id`          BIGINT       NOT NULL COMMENT '主键ID',
    `role_name`   VARCHAR(50)  NOT NULL COMMENT '角色名称',
    `role_code`   VARCHAR(50)  NOT NULL COMMENT '角色编码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态（0=禁用，1=正常）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除（0=正常，1=已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ==================== 用户角色关联表 ====================
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `id`      BIGINT NOT NULL COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ==================== 菜单表 ====================
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
    `id`          BIGINT       NOT NULL COMMENT '主键ID',
    `parent_id`   BIGINT       NOT NULL DEFAULT 0 COMMENT '父菜单ID（0=顶级菜单）',
    `menu_name`   VARCHAR(50)  NOT NULL COMMENT '菜单名称',
    `menu_type`   VARCHAR(20)  NOT NULL DEFAULT 'menu' COMMENT '菜单类型（catalog=目录，menu=菜单，button=按钮）',
    `path`        VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
    `component`   VARCHAR(200) DEFAULT NULL COMMENT '前端组件路径',
    `icon`        VARCHAR(50)  DEFAULT NULL COMMENT '菜单图标',
    `permission`  VARCHAR(100) DEFAULT NULL COMMENT '权限标识（如 sys:user:add）',
    `sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序（数值越小越靠前）',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态（0=隐藏，1=显示）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除（0=正常，1=已删除）',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ==================== 角色菜单关联表 ====================
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
    `id`      BIGINT NOT NULL COMMENT '主键ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- ==================== 初始化数据 ====================

-- 管理员用户（密码在应用启动时由 DataInitializer 自动创建）
-- 如不希望自动创建，请取消注释下面语句并确保密码为 BCrypt 加密：
-- INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `email`, `status`) VALUES
-- (1, 'admin', 'BCRYPT_HASH_OF_123456', '超级管理员', 'admin@ccdemo.com', 1);

-- 角色数据
INSERT INTO `role` (`id`, `role_name`, `role_code`, `description`, `status`) VALUES
(1, '超级管理员', 'admin', '拥有所有权限', 1),
(2, '普通用户', 'user', '基础用户权限', 1);

-- 分配管理员角色
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES
(1, 1, 1);

-- 菜单数据（目录 → 菜单 → 按钮 三级结构）
INSERT INTO `menu` (`id`, `parent_id`, `menu_name`, `menu_type`, `path`, `component`, `icon`, `permission`, `sort`, `status`) VALUES
-- 一级目录
(1,  0, '系统管理', 'catalog', '/system', NULL, 'Setting', NULL, 1, 1),
(2,  0, '仪表盘',   'catalog', '/dashboard', NULL, 'DataBoard', NULL, 0, 1),

-- 系统管理 → 二级菜单
(10, 1, '用户管理', 'menu', '/system/user', 'system/user/index', 'User', 'sys:user:list', 1, 1),
(11, 1, '角色管理', 'menu', '/system/role', 'system/role/index', 'Avatar', 'sys:role:list', 2, 1),
(12, 1, '菜单管理', 'menu', '/system/menu', 'system/menu/index', 'Menu', 'sys:menu:list', 3, 1),

-- 用户管理 → 三级按钮
(100, 10, '新增用户', 'button', NULL, NULL, NULL, 'sys:user:add', 1, 1),
(101, 10, '编辑用户', 'button', NULL, NULL, NULL, 'sys:user:edit', 2, 1),
(102, 10, '删除用户', 'button', NULL, NULL, NULL, 'sys:user:delete', 3, 1),
(103, 10, '分配角色', 'button', NULL, NULL, NULL, 'sys:user:assign-role', 4, 1),

-- 角色管理 → 三级按钮
(110, 11, '新增角色', 'button', NULL, NULL, NULL, 'sys:role:add', 1, 1),
(111, 11, '编辑角色', 'button', NULL, NULL, NULL, 'sys:role:edit', 2, 1),
(112, 11, '删除角色', 'button', NULL, NULL, NULL, 'sys:role:delete', 3, 1),
(113, 11, '分配菜单', 'button', NULL, NULL, NULL, 'sys:role:assign-menu', 4, 1),

-- 菜单管理 → 三级按钮
(120, 12, '新增菜单', 'button', NULL, NULL, NULL, 'sys:menu:add', 1, 1),
(121, 12, '编辑菜单', 'button', NULL, NULL, NULL, 'sys:menu:edit', 2, 1),
(122, 12, '删除菜单', 'button', NULL, NULL, NULL, 'sys:menu:delete', 3, 1),

-- 仪表盘 → 二级菜单
(20, 2, '首页', 'menu', '/dashboard', 'dashboard/index', 'HomeFilled', NULL, 1, 1);

-- 分配管理员所有菜单权限
INSERT INTO `role_menu` (`id`, `role_id`, `menu_id`)
SELECT
    (@rownum := @rownum + 1) AS id,
    1 AS role_id,
    m.id AS menu_id
FROM `menu` m, (SELECT @rownum := 0) r;
