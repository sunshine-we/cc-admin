# ccAdmin — 后台管理系统脚手架

> 前后端分离的 Java Web 后台管理脚手架，开箱即用的 RBAC 权限管理系统。

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4-42b883.svg)](https://vuejs.org/)
[![Element Plus](https://img.shields.io/badge/Element%20Plus-2.5-409eff.svg)](https://element-plus.org/)

---

## ✨ 特性

- 🔐 **JWT 认证** — 双 Token 机制（accessToken + refreshToken），自动刷新
- 👤 **RBAC 权限** — 用户 → 角色 → 菜单 三级权限控制，支持按钮级鉴权
- 🎨 **多主题换肤** — 内置浅色/深色/蓝色/绿色/紫色 5 种主题，一键切换
- 📋 **菜单管理** — 树形菜单管理（目录/菜单/按钮），动态路由生成
- 📖 **API 文档** — Knife4j 自动生成，交互式 Swagger 文档
- 🔧 **代码生成器** — MyBatis Plus Generator，一键生成 CRUD 代码
- 📊 **仪表盘** — 系统概览、快捷入口
- 🌐 **前后端分离** — Vue 3 + Spring Boot，独立开发部署

---

## 🛠 技术栈

| 层面 | 技术 | 版本 |
|------|------|------|
| **后端框架** | Spring Boot | 3.2.0 |
| **ORM** | MyBatis Plus | 3.5.5 |
| **安全认证** | jjwt + BCrypt | 0.12.3 |
| **API 文档** | Knife4j (Swagger) | 4.5.0 |
| **工具库** | Hutool + Lombok | 5.8.25 |
| **数据库** | MySQL | 8.x |
| **前端框架** | Vue 3 (Composition API) | 3.4 |
| **构建工具** | Vite | 5.x |
| **UI 组件库** | Element Plus | 2.5 |
| **状态管理** | Pinia | 2.x |
| **路由** | Vue Router | 4.x |
| **HTTP 客户端** | Axios | 1.6 |

---

## 🚀 快速开始

### 环境要求

- **JDK** 17+
- **Maven** 3.8+
- **MySQL** 8.0+
- **Node.js** 18+

### 1. 数据库初始化

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS ccdemo DEFAULT CHARSET utf8mb4;"

# 导入初始化脚本（可选 — DataInitializer 也会自动建表）
mysql -u root -p ccdemo < backend/sql/init.sql
```

### 2. 启动后端

```bash
cd backend

# 修改数据库连接（如果与默认不同）
# 编辑 src/main/resources/application-dev.yml
#   spring.datasource.url=jdbc:mysql://localhost:3306/ccdemo
#   spring.datasource.username=root
#   spring.datasource.password=root

# 启动
mvn spring-boot:run

# 或使用 IDE 运行 CcDemoApplication.java
```

后端启动后访问：
- 🌐 API 服务：http://localhost:8080
- 📖 API 文档：http://localhost:8080/doc.html

### 3. 启动前端

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端启动后访问：
- 🖥 管理后台：http://localhost:5173

### 4. 登录

- **默认账号**：`admin`
- **默认密码**：`123456`

---

## 📁 项目结构

```
cc-admin/
├── README.md                    # 项目说明
├── backend/                     # 后端 Spring Boot 项目
│   ├── pom.xml                  # Maven 配置
│   ├── sql/
│   │   └── init.sql             # 数据库初始化脚本
│   └── src/main/
│       ├── java/com/example/ccdemo/
│       │   ├── CcDemoApplication.java
│       │   ├── common/          # 公共组件（JWT/拦截器/异常/配置）
│       │   ├── modules/
│       │   │   ├── auth/        # 认证模块
│       │   │   ├── user/        # 用户管理
│       │   │   ├── role/        # 角色管理
│       │   │   └── menu/        # 菜单管理
│       │   └── util/
│       │       └── GeneratorCode.java  # 代码生成器
│       └── resources/
│           ├── application.yml
│           └── application-dev.yml
├── frontend/                    # 前端 Vue 3 项目
│   ├── package.json
│   ├── vite.config.js
│   └── src/
│       ├── main.js              # 应用入口
│       ├── App.vue
│       ├── api/                 # API 接口层
│       ├── router/              # 路由配置
│       ├── stores/              # Pinia 状态管理
│       │   ├── app.js           # 应用全局状态
│       │   ├── user.js          # 用户认证状态
│       │   └── theme.js         # 主题换肤状态 ✨
│       ├── views/               # 页面组件
│       │   ├── login/           # 登录页
│       │   ├── dashboard/       # 仪表盘
│       │   ├── system/          # 系统管理页面
│       │   └── error/           # 403/404 错误页
│       ├── components/          # 布局组件
│       │   └── layout/
│       │       ├── MainLayout.vue
│       │       ├── Sidebar.vue
│       │       └── Navbar.vue
│       ├── styles/              # 主题样式
│       │   └── themes/
│       │       ├── light.css    # 浅色主题
│       │       ├── dark.css     # 深色主题 ✨
│       │       ├── blue.css     # 蓝色主题 ✨
│       │       ├── green.css    # 绿色主题 ✨
│       │       └── purple.css   # 紫色主题 ✨
│       └── utils/               # 工具函数
└── .gitignore
```

---

## 🎨 主题换肤

ccAdmin 内置 **5 种主题**，点击导航栏右侧的 🌙 图标即可切换：

| 主题 | 说明 | 预览 |
|------|------|------|
| ☀️ 浅色模式 | 经典白色背景，适合日常办公 | 默认 |
| 🌙 深色模式 | 暗黑护眼，适合夜间使用 | `dark` |
| 💙 蓝色主题 | 蓝色主色调，清爽专业 | `blue` |
| 💚 绿色主题 | 绿色主色调，清新自然 | `green` |
| 💜 紫色主题 | 紫色主色调，华丽优雅 | `purple` |

**实现原理**：
- 所有颜色值通过 CSS 自定义属性（`--color-primary`、`--bg-page` 等）控制
- 切换主题时修改 `<html data-theme="xxx">` 属性，所有组件自动响应
- 主题选择持久化到 `localStorage`，刷新不丢失
- 登录页和主界面均可切换主题

**添加新主题**：
1. 在 `src/styles/themes/` 下新建 `xxx.css`
2. 在 `[data-theme='xxx']` 选择器下覆盖 CSS 变量
3. 在 `src/main.js` 中引入新主题
4. 在 `src/stores/theme.js` 的 `VALID_THEMES` 和 `Navbar.vue` 的 `themeOptions` 中添加

---

## 📡 API 接口概览

| 模块 | 路径前缀 | 说明 |
|------|----------|------|
| 认证 | `/auth` | 登录/注册/刷新Token/退出 |
| 用户管理 | `/user` | 用户 CRUD + 分页 + 角色分配 |
| 角色管理 | `/role` | 角色 CRUD + 菜单权限分配 |
| 菜单管理 | `/menu` | 菜单树管理 + 用户菜单查询 |

> 完整 API 文档请访问：http://localhost:8080/doc.html

---

## 🔧 代码生成器

使用 MyBatis Plus Generator 快速生成 CRUD 代码：

1. 修改 `backend/src/main/java/com/example/ccdemo/util/GeneratorCode.java`
2. 设置要生成的表名
3. 运行 `GeneratorCode.main()` 方法
4. 生成的代码自动放入对应的 `modules/<模块名>/` 目录

---

## 🧪 运行测试

```bash
# 后端测试
cd backend
mvn test

# 前端构建
cd frontend
npm run build
```

---

## 📝 开发指南

### 环境变量

**后端** (`application-dev.yml`)：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ccdemo
    username: root
    password: root

jwt:
  secret: your-secret-key-must-be-long-enough
  expiration: 86400000       # accessToken 过期时间（ms）
  refresh-expiration: 604800000  # refreshToken 过期时间（ms）
```

**前端** (`.env.development`)：

```env
VITE_API_BASE_URL=http://localhost:8080
```

### 前端代理

开发环境下，Vite 已配置代理将 `/api` 请求转发到后端 `http://localhost:8080`，无需单独配置 CORS。

---

## 📄 License

[MIT License](./LICENSE)

---

> 🤖 由 Spring Boot 3.x + Vue 3 + Element Plus 构建
