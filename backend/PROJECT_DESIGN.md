# ccDemo 基础脚手架项目设计文档

## 一、项目概述

构建一个基础 JavaWeb 脚手架项目，前后端分离架构，后端提供 RESTful API，前端独立开发部署。预置用户登录/JWT 认证、RBAC 权限管理（用户/角色/菜单）、MyBatis Plus 代码生成器等基础功能。

## 二、技术选型

| 层次 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 语言 | Java | 17 | LTS 版本 |
| 框架 | Spring Boot | 3.2.0 | 最新稳定版 |
| ORM | MyBatis Plus | 3.5.5 | 增强 MyBatis |
| 数据库 | MySQL | 8.x | 关系型数据库 |
| 模板引擎 | Thymeleaf | - | 保留依赖，暂不使用 |
| 构建工具 | Maven | 3.8+ | 传统 XML 配置 |
| API 文档 | Knife4j | 4.5.0 | Swagger 增强版 |
| 认证 | jjwt | 0.12.3 | JWT 实现 |
| 工具库 | Lombok + Hutool | - | 简化开发 |
| 测试 | JUnit 5 + Mockito | - | Spring Boot 默认 |
| 密码加密 | BCrypt | - | Spring Security Crypto |
| 前端框架 | Vue 3 + Vite | 最新 | 现代化前端工具链 |
| UI 组件库 | Element Plus | 最新 | Vue 3 生态 |
| 路由 | Vue Router 4 | 4.x | SPA 路由 |
| 状态管理 | Pinia | 2.x | Vue 官方推荐 |
| HTTP 客户端 | Axios | 最新 | Promise 风格 |

## 三、后端项目结构

```
ccDemo/
├── pom.xml                              # Maven 配置
├── sql/
│   └── init.sql                         # 数据库初始化脚本（建表+种子数据）
├── src/
│   ├── main/
│   │   ├── java/com/example/ccdemo/
│   │   │   ├── CcDemoApplication.java   # 启动类
│   │   │   ├── common/                   # 公共组件层
│   │   │   │   ├── annotation/
│   │   │   │   │   └── IgnoreAuth.java              # 跳过 JWT 认证注解
│   │   │   │   ├── base/
│   │   │   │   │   └── BaseEntity.java              # 实体基类（id/createTime/updateTime/deleted）
│   │   │   │   ├── config/
│   │   │   │   │   ├── CorsConfig.java              # 跨域配置
│   │   │   │   │   ├── DataInitializer.java         # 首次启动自动创建 admin 账号
│   │   │   │   │   ├── JacksonConfig.java           # JSON 序列化（日期格式化）
│   │   │   │   │   ├── Knife4jConfig.java           # API 文档配置
│   │   │   │   │   ├── MyBatisPlusConfig.java       # 分页插件
│   │   │   │   │   ├── MyMetaObjectHandler.java     # 自动填充 createTime/updateTime
│   │   │   │   │   ├── SecurityConfig.java           # BCryptPasswordEncoder Bean
│   │   │   │   │   └── WebMvcConfig.java            # 拦截器注册
│   │   │   │   ├── exception/
│   │   │   │   │   ├── BusinessException.java       # 业务异常
│   │   │   │   │   └── GlobalExceptionHandler.java  # 全局异常处理（@RestControllerAdvice）
│   │   │   │   ├── jwt/
│   │   │   │   │   ├── JwtInterceptor.java          # JWT 拦截器（校验 Token）
│   │   │   │   │   ├── JwtUserContext.java          # ThreadLocal 用户上下文
│   │   │   │   │   └── JwtUtil.java                 # JWT 生成/解析/校验工具
│   │   │   │   └── result/
│   │   │   │       ├── Result.java                  # 统一响应体 {code, message, data, timestamp}
│   │   │   │       └── ResultCode.java              # 响应码枚举（200/400/401/403/500/1xxx/2xxx/3xxx）
│   │   │   ├── modules/                 # 业务模块层
│   │   │   │   ├── auth/                # 认证模块
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── AuthController.java      # POST /auth/login, /auth/register, /auth/refresh-token, /auth/logout, GET /auth/current-user
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── LoginDTO.java            # 登录请求
│   │   │   │   │   │   ├── LoginResultVO.java       # 登录响应（accessToken, refreshToken, userInfo）
│   │   │   │   │   │   └── RegisterDTO.java         # 注册请求
│   │   │   │   │   └── service/
│   │   │   │   │       ├── AuthService.java
│   │   │   │   │       └── impl/AuthServiceImpl.java
│   │   │   │   ├── user/                # 用户管理模块
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── UserController.java      # CRUD + 分页 + 分配角色 + 重置密码
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   └── User.java                # 用户实体
│   │   │   │   │   ├── mapper/
│   │   │   │   │   │   └── UserMapper.java          # BaseMapper<User>
│   │   │   │   │   └── service/
│   │   │   │   │       ├── UserService.java         # extends IService<User>
│   │   │   │   │       └── impl/UserServiceImpl.java
│   │   │   │   ├── role/                # 角色管理模块
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── RoleController.java      # CRUD + 分页 + 分配菜单
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Role.java                # 角色实体
│   │   │   │   │   │   └── UserRole.java            # 用户-角色关联
│   │   │   │   │   ├── mapper/
│   │   │   │   │   │   ├── RoleMapper.java
│   │   │   │   │   │   └── UserRoleMapper.java
│   │   │   │   │   └── service/
│   │   │   │   │       ├── RoleService.java
│   │   │   │   │       └── impl/RoleServiceImpl.java
│   │   │   │   └── menu/                # 菜单管理模块
│   │   │   │       ├── controller/
│   │   │   │       │   └── MenuController.java      # 菜单树 + CRUD + 用户菜单
│   │   │   │       ├── entity/
│   │   │   │       │   ├── Menu.java                # 菜单实体（含 children 字段）
│   │   │   │       │   └── RoleMenu.java            # 角色-菜单关联
│   │   │   │       ├── mapper/
│   │   │   │       │   ├── MenuMapper.java
│   │   │   │       │   └── RoleMenuMapper.java
│   │   │   │       └── service/
│   │   │   │           ├── MenuService.java
│   │   │   │           └── impl/MenuServiceImpl.java
│   │   │   └── util/
│   │   │       └── GeneratorCode.java              # MyBatis Plus 代码生成器（独立运行）
│   │   └── resources/
│   │       ├── application.yml          # 公共配置（端口/MyBatisPlus/JWT/Knife4j）
│   │       ├── application-dev.yml      # 开发环境（MySQL 数据源/日志）
│   │       └── mapper/                  # MyBatis XML 映射文件目录
│   └── test/java/com/example/ccdemo/
│       └── CcDemoApplicationTests.java  # 基础测试（JWT/BCrypt）
```

## 四、数据库设计

### 4.1 表结构（5 张核心表）

```sql
-- 用户表
user (
    id          BIGINT PRIMARY KEY,     -- 雪花算法
    username    VARCHAR(50) UNIQUE,     -- 用户名
    password    VARCHAR(255),           -- BCrypt 加密
    nickname    VARCHAR(50),            -- 昵称
    email       VARCHAR(100),           -- 邮箱
    phone       VARCHAR(20),            -- 手机号
    avatar      VARCHAR(255),           -- 头像 URL
    status      TINYINT DEFAULT 1,      -- 0=禁用, 1=正常
    create_time DATETIME,
    update_time DATETIME,
    deleted     TINYINT DEFAULT 0       -- 逻辑删除
)

-- 角色表
role (
    id          BIGINT PRIMARY KEY,
    role_name   VARCHAR(50),            -- 角色名称
    role_code   VARCHAR(50) UNIQUE,     -- 角色编码
    description VARCHAR(200),           -- 角色描述
    status      TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME,
    deleted     TINYINT DEFAULT 0
)

-- 用户-角色关联表
user_role (
    id      BIGINT PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT
)

-- 菜单表
menu (
    id          BIGINT PRIMARY KEY,
    parent_id   BIGINT DEFAULT 0,       -- 父菜单 ID
    menu_name   VARCHAR(50),            -- 菜单名称
    menu_type   VARCHAR(20),            -- catalog/menu/button
    path        VARCHAR(200),           -- 路由路径
    component   VARCHAR(200),           -- 前端组件
    icon        VARCHAR(50),            -- 图标
    permission  VARCHAR(100),           -- 权限标识（如 sys:user:add）
    sort        INT DEFAULT 0,          -- 排序
    status      TINYINT DEFAULT 1,      -- 0=隐藏, 1=显示
    create_time DATETIME,
    update_time DATETIME,
    deleted     TINYINT DEFAULT 0
)

-- 角色-菜单关联表
role_menu (
    id      BIGINT PRIMARY KEY,
    role_id BIGINT,
    menu_id BIGINT
)
```

### 4.2 种子数据

- **管理员账号**: admin / 123456（由 DataInitializer 启动时自动创建）
- **角色**: 超级管理员(admin) + 普通用户(user)
- **菜单**: 系统管理（用户管理/角色管理/菜单管理）+ 仪表盘，三级树形结构

## 五、核心功能设计

### 5.1 统一响应体

```json
{
    "code": 200,         // 状态码
    "message": "操作成功", // 提示信息
    "data": {},          // 数据（泛型）
    "timestamp": 1700000000000  // 时间戳
}
```

### 5.2 JWT 认证流程

```
1. POST /auth/login → 校验账号密码(BCrypt) → 返回 {accessToken, refreshToken}
2. 前端存储 Token，每次请求 Header: "Authorization: Bearer <token>"
3. JwtInterceptor 拦截所有请求（@IgnoreAuth 标记的除外），校验 Token
4. Token 过期: 24h，RefreshToken: 7d
5. POST /auth/refresh-token → 使用 refreshToken 换取新 Token
```

### 5.3 RBAC 权限模型

- 用户 ↔ 角色（多对多）→ 菜单/按钮（多对多）
- 前端通过 `/menu/user-menu` 动态获取菜单树
- 按钮级权限通过 `permission` 字段控制（如 `sys:user:add`）

### 5.4 全局异常处理

- `@RestControllerAdvice` 统一捕获
- `BusinessException` 携带自定义错误码
- 参数校验失败自动返回友好提示

### 5.5 代码生成器

`GeneratorCode.java` 独立运行，连接数据库自动生成 entity/mapper/service/controller/XML。

## 六、前端项目结构

```
ccDemo-ui/
├── package.json
├── vite.config.js
├── index.html
├── .env.development             # VITE_API_BASE_URL=http://localhost:8080
├── .env.production              # VITE_API_BASE_URL=/
├── src/
│   ├── main.js                  # 创建 App + 注册 Router/Pinia/ElementPlus
│   ├── App.vue                  # 根组件
│   ├── style.css                # 全局样式
│   ├── api/
│   │   ├── request.js           # Axios 实例（baseURL/拦截器/Token 注入/错误处理）
│   │   └── modules/
│   │       ├── auth.js          # login/register/refreshToken/getCurrentUser/logout
│   │       ├── user.js          # page/add/update/delete/assignRoles/getRoles/resetPassword
│   │       ├── role.js          # page/list/add/update/delete/assignMenus/getMenus
│   │       └── menu.js          # tree/userMenu/add/update/delete
│   ├── router/
│   │   └── index.js             # 路由表 + 全局前置守卫（Token 校验）
│   ├── stores/
│   │   ├── user.js              # Token/用户信息/登录状态
│   │   └── app.js               # 侧边栏折叠/全局设置
│   ├── views/
│   │   ├── login/index.vue      # 登录页
│   │   ├── dashboard/index.vue  # 仪表盘
│   │   ├── error/
│   │   │   ├── 403.vue          # 无权限
│   │   │   └── 404.vue          # 未找到
│   │   └── system/
│   │       ├── user/index.vue   # 用户管理（表格+搜索+弹窗）
│   │       ├── role/index.vue   # 角色管理（表格+搜索+弹窗+菜单树分配）
│   │       └── menu/index.vue   # 菜单管理（树形表格+弹窗）
│   ├── components/
│   │   └── layout/
│   │       ├── MainLayout.vue   # 主布局（侧边栏+顶部+内容区）
│   │       ├── Sidebar.vue      # 动态菜单侧边栏（支持折叠/递归菜单）
│   │       └── Navbar.vue       # 顶部导航栏（用户头像/退出）
│   └── utils/
│       ├── auth.js              # getToken/setToken/removeToken
│       └── index.js             # 通用工具函数
```

## 七、API 接口清单

### 7.1 认证模块 (`/auth`)

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | /auth/login | 用户登录 | 否 |
| POST | /auth/register | 用户注册 | 否 |
| POST | /auth/refresh-token | 刷新 Token | 否 |
| POST | /auth/logout | 退出登录 | 是 |
| GET | /auth/current-user | 获取当前用户信息 | 是 |

### 7.2 用户管理 (`/user`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /user/page | 分页查询用户 |
| GET | /user/{id} | 根据 ID 查询 |
| POST | /user | 新增用户 |
| PUT | /user | 编辑用户 |
| DELETE | /user/{id} | 删除用户 |
| PUT | /user/{userId}/roles | 分配角色 |
| GET | /user/{userId}/roles | 获取用户角色 |
| PUT | /user/{id}/reset-password | 重置密码 |

### 7.3 角色管理 (`/role`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /role/page | 分页查询角色 |
| GET | /role/list | 获取所有角色 |
| GET | /role/{id} | 根据 ID 查询 |
| POST | /role | 新增角色 |
| PUT | /role | 编辑角色 |
| DELETE | /role/{id} | 删除角色 |
| PUT | /role/{roleId}/menus | 分配菜单 |
| GET | /role/{roleId}/menus | 获取角色菜单 |

### 7.4 菜单管理 (`/menu`)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /menu/tree | 获取全部菜单树 |
| GET | /menu/user-menu | 获取当前用户菜单 |
| GET | /menu/{id} | 查询单个菜单 |
| POST | /menu | 新增菜单 |
| PUT | /menu | 编辑菜单 |
| DELETE | /menu/{id} | 删除菜单（含子菜单） |

## 八、启动说明

### 8.1 后端启动

1. 确保 MySQL 已安装并运行
2. 执行 `sql/init.sql` 建表（或让 DataInitializer 自动创建）
3. 修改 `application-dev.yml` 中的数据库连接参数
4. 运行 `CcDemoApplication.main()` 方法
5. 访问 API 文档: http://localhost:8080/doc.html
6. 默认账号: admin / 123456

### 8.2 前端启动

1. `cd ccDemo-ui`
2. `npm install` 安装依赖
3. `npm run dev` 启动开发服务器
4. 访问 http://localhost:5173

### 8.3 代码生成器

1. 修改 `GeneratorCode.java` 中的表名
2. 运行 `GeneratorCode.main()` 方法
3. 生成的代码位于 `modules/<模块名>/` 下

## 九、配置文件关键参数

```yaml
# application.yml
server.port: 8080
jwt.secret: ccDemoSecretKey2024ForJwtTokenGenerationMustBeLongEnough
jwt.expiration: 86400000        # 24h
jwt.refresh-expiration: 604800000  # 7d

# application-dev.yml
spring.datasource.url: jdbc:mysql://localhost:3306/ccdemo
spring.datasource.username: root
spring.datasource.password: root
```

## 十、依赖清单（pom.xml 核心依赖）

| GroupId | ArtifactId | Version |
|---------|-----------|---------|
| org.springframework.boot | spring-boot-starter-web | 3.2.0 |
| org.springframework.boot | spring-boot-starter-validation | 3.2.0 |
| org.springframework.boot | spring-boot-starter-thymeleaf | 3.2.0 |
| org.springframework.security | spring-security-crypto | 3.2.0 |
| com.mysql | mysql-connector-j | - |
| com.baomidou | mybatis-plus-spring-boot3-starter | 3.5.5 |
| com.baomidou | mybatis-plus-generator | 3.5.5 |
| com.github.xiaoymin | knife4j-openapi3-jakarta-spring-boot-starter | 4.5.0 |
| io.jsonwebtoken | jjwt-api | 0.12.3 |
| cn.hutool | hutool-all | 5.8.25 |
| org.projectlombok | lombok | - |
| org.springframework.boot | spring-boot-starter-test | 3.2.0 |
