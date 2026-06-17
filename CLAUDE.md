# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 语言

始终使用中文与用户交流。所有对话、解释和代码注释都应使用中文。

## 自动提交规则

每当你完成一个独立功能的开发，或修复完一个 Bug 并验证通过后，必须自动执行 `git add -A` 和 `git commit`，并生成一句简洁的中文 commit message（不超过 50 字）。commit message 请遵循以下约定：

- `feat: xxxx` — 新功能
- `fix: xxxx` — Bug 修复
- `refactor: xxxx` — 重构
- `style: xxxx` — 样式调整
- `docs: xxxx` — 文档变更

## 常用命令

```bash
# 后端
cd backend
mvn spring-boot:run                    # 启动后端（端口 8080）
mvn test                                # 运行所有测试
mvn spring-boot:run -Dspring-boot.run.profiles=dev  # 指定 dev 环境启动

# 前端
cd frontend
npm install                             # 安装依赖
npm run dev                             # 启动开发服务器（端口 5173）
npm run build                           # 生产构建
```

## 架构概览

### 后端分层

```
controller → service → mapper (MyBatis Plus BaseMapper)
     ↓
拦截器层: WebMvcConfig 注册 JwtInterceptor（除 @IgnoreAuth 标注外全部拦截）
     ↓
全局异常处理层: GlobalExceptionHandler (@RestControllerAdvice)
     ↓
统一响应: Result<T> {code, message, data, timestamp}
```

### 认证与鉴权链路

1. `POST /auth/login` → AuthService 用 `BCryptPasswordEncoder` 校验密码 → 返回 `{accessToken, refreshToken}`
2. 前端 `request.js` 拦截器在每个请求 Header 注入 `Authorization: Bearer <token>`
3. 后端 `JwtInterceptor` 校验 Token → 解析出 userId 存入 `JwtUserContext`（ThreadLocal）
4. accessToken 过期 24h，refreshToken 过期 7d — 前端通过 `/auth/refresh-token` 刷新
5. 密码加密仅依赖 `spring-security-crypto`（不含完整 Spring Security 框架）

### RBAC 数据模型

```
User ──many-to-many── UserRole ── Role ──many-to-many── RoleMenu ── Menu
```

- `Menu` 表是树形结构（`parentId` 自引用），三级类型: `catalog`(目录) / `menu`(菜单) / `button`(按钮)
- 前端侧边栏通过 `GET /menu/user-menu` 获取当前用户的菜单树，动态渲染
- 按钮级权限通过 `Menu.permission` 字段（如 `sys:user:add`）控制

### MyBatis Plus 约定

- 主键策略: `ASSIGN_ID`（雪花算法 Long）
- 逻辑删除: `deleted` 字段（1=已删除，0=正常）
- 自动填充: `createTime` / `updateTime`，由 `MyMetaObjectHandler` 处理
- 实体扫描路径: `com.example.ccdemo.modules.*.entity`
- SQL 日志: `StdOutImpl`（开发环境控制台打印）

### 前端设计规范

**创建或修改前端页面/组件时，必须先调用以下 Skill：**

- `frontend-design` — 用于创造性 UI 工作（新页面、新组件、布局重设计），生成高质量、非模板化的界面代码
- `ui-ux-pro-max` — 用于 UI/UX 决策（配色方案、字体搭配、间距、交互状态、动效、无障碍），覆盖 50+ 设计风格和 161 套配色

**新页面开发流程：**

1. 调用 `frontend-design` 获取设计方向和代码框架
2. 调用 `ui-ux-pro-max` 验证配色/字体/间距与现有主题系统的兼容性
3. 实现时必须使用项目 CSS 变量（`--color-primary`、`--bg-page`、`--text-primary` 等），确保 5 套主题下均正常显示
4. 布局组件复用 `MainLayout.vue` / `Sidebar.vue` / `Navbar.vue`，页面放在 `src/views/<模块>/`
5. API 调用通过 `src/api/modules/` 下的模块文件，使用 `request.js` 导出的 axios 实例

**新增页面必须适配换肤的检查清单：**

- [ ] 所有颜色值使用 CSS 变量，不硬编码（检查：`grep -E '#[0-9a-fA-F]{3,6}' src/views/新页面.vue` 应无输出）
- [ ] `background`、`color`、`border-color`、`box-shadow` 均引用 `var(--xxx)`
- [ ] 表格/卡片/对话框等 Element Plus 组件的样式覆盖在 `style.css` 中已全局处理
- [ ] 深色主题下文字可读，对比度不低于 4.5:1

### 前端主题系统

- 所有颜色通过 CSS 自定义属性控制（定义在 `src/styles/themes/*.css`）
- 切换主题时修改 `<html data-theme="xxx">`，深色模式额外添加 `class="dark"` 启用 Element Plus 暗黑变量
- `src/stores/theme.js` 管理状态，`localStorage` 持久化
- `index.html` 中的内联 `<script>` 在 Vue 挂载前设置属性，防止闪烁

### 前端请求链路

```
Vue 组件 → API 模块（import request）→ axios 实例
    ├── 请求拦截: 注入 Bearer Token
    └── 响应拦截: code===200 正常返回 / 401 跳登录 / 其他展示 ElMessage
```

Vite 开发代理: `/api/**` → `http://localhost:8080/**`（去掉 `/api` 前缀）

### 启动初始化

`DataInitializer`（CommandLineRunner）在首次启动时自动创建：
- 角色: `admin`（超级管理员）+ `user`（普通用户）
- 用户: `admin / 123456`（BCrypt 加密），并分配 admin 角色
- 如果表已有数据则跳过（`selectCount(null) == 0` 判断）

### 后端配置关键点

| 配置项 | 文件 | 说明 |
|--------|------|------|
| `jwt.secret` | application.yml | JWT 签名密钥 |
| `jwt.expiration` | application.yml | Token 有效期 86400000ms = 24h |
| `jwt.refresh-expiration` | application.yml | RefreshToken 有效期 604800000ms = 7d |
| `spring.datasource` | application-dev.yml | MySQL 连接，默认 `root/root`，库名 `ccdemo` |
| `mybatis-plus.global-config.db-config.logic-delete-field` | application.yml | 逻辑删除字段名 `deleted` |
