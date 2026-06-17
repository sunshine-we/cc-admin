<template>
  <div class="dashboard">
    <div class="dashboard-header">
      <h2 class="greeting">欢迎使用 ccAdmin 管理后台</h2>
      <p class="greeting-sub">Spring Boot 3.x + Vue 3 后台管理脚手架</p>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-inner">
            <div class="stat-icon" style="background: var(--color-primary-bg)">
              <el-icon :size="28" color="var(--color-primary)"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">1,024</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-inner">
            <div class="stat-icon" style="background: #fdf2f0">
              <el-icon :size="28" color="#f56c6c"><Avatar /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">8</div>
              <div class="stat-label">角色数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-inner">
            <div class="stat-icon" style="background: #ecfdf5">
              <el-icon :size="28" color="#67c23a"><Menu /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">32</div>
              <div class="stat-label">菜单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-inner">
            <div class="stat-icon" style="background: #fef3e2">
              <el-icon :size="28" color="#e6a23c"><DataBoard /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">5,120</div>
              <div class="stat-label">今日访问</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 内容卡片 -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span class="card-title">系统信息</span>
          </template>
          <div class="info-list">
            <div class="info-item">
              <span class="label">系统名称</span>
              <span class="value">ccAdmin 脚手架</span>
            </div>
            <div class="info-item">
              <span class="label">版本</span>
              <el-tag size="small" type="primary">v1.0.0</el-tag>
            </div>
            <div class="info-item">
              <span class="label">前端框架</span>
              <span class="value">Vue 3 + Element Plus + Vite</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span class="card-title">技术栈</span>
          </template>
          <div class="info-list">
            <div class="info-item">
              <span class="label">后端</span>
              <span class="value">Spring Boot 3.x</span>
            </div>
            <div class="info-item">
              <span class="label">ORM</span>
              <span class="value">MyBatis Plus</span>
            </div>
            <div class="info-item">
              <span class="label">数据库</span>
              <span class="value">MySQL 8.x</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span class="card-title">快捷入口</span>
          </template>
          <div class="quick-links">
            <el-link type="primary" href="/system/user">
              <el-icon><User /></el-icon>
              用户管理
            </el-link>
            <el-link type="primary" href="/system/role">
              <el-icon><Avatar /></el-icon>
              角色管理
            </el-link>
            <el-link type="primary" href="/system/menu">
              <el-icon><Menu /></el-icon>
              菜单管理
            </el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { User, Avatar, Menu, DataBoard } from '@element-plus/icons-vue'
</script>

<style scoped>
.dashboard-header {
  margin-bottom: 24px;
}

.greeting {
  color: var(--text-primary);
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 6px;
  transition: color 0.3s ease;
}

.greeting-sub {
  color: var(--text-secondary);
  font-size: 14px;
}

/* ---- 统计卡片 ---- */
.stat-row {
  margin-bottom: 0;
}

.stat-card {
  border-radius: 10px;
}

.stat-inner {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.stat-number {
  font-size: 26px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1.2;
  transition: color 0.3s ease;
}

.stat-label {
  font-size: 13px;
  color: var(--text-secondary);
  margin-top: 2px;
}

/* ---- 卡片标题 ---- */
.card-title {
  font-weight: 600;
  color: var(--text-primary);
  transition: color 0.3s ease;
}

/* ---- 信息列表 ---- */
.info-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.info-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.info-item .label {
  color: var(--text-secondary);
  font-size: 14px;
}

.info-item .value {
  color: var(--text-primary);
  font-size: 14px;
  font-weight: 500;
}

/* ---- 快捷入口 ---- */
.quick-links {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.quick-links .el-link {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  padding: 8px 12px;
  border-radius: 6px;
  transition: background-color 0.2s;
}

.quick-links .el-link:hover {
  background-color: var(--bg-hover);
}
</style>
