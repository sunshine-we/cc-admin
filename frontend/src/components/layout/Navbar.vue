<template>
  <el-header class="navbar">
    <div class="left">
      <el-icon class="collapse-btn" @click="appStore.toggleSidebar()">
        <Fold v-if="!appStore.sidebarCollapsed" />
        <Expand v-else />
      </el-icon>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="route.meta.title && route.path !== '/dashboard'">
          {{ route.meta.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="right">
      <!-- 主题切换 -->
      <el-dropdown trigger="click" @command="handleThemeChange" class="theme-dropdown">
        <span class="theme-trigger">
          <el-icon :size="18">
            <Moon v-if="themeStore.currentTheme === 'dark'" />
            <Sunny v-else />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item
              v-for="t in themeOptions"
              :key="t.value"
              :command="t.value"
              :class="{ 'is-active': themeStore.currentTheme === t.value }"
            >
              <span class="theme-dot" :style="{ background: t.color }"></span>
              {{ t.label }}
              <el-icon v-if="themeStore.currentTheme === t.value" class="check-icon">
                <Check />
              </el-icon>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!-- 用户信息 -->
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="user-info">
          <el-avatar :size="32" :icon="UserFilled" />
          <span class="username">{{ userStore.userInfo?.nickname || userStore.userInfo?.username || '管理员' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'
import { useThemeStore } from '@/stores/theme'
import { UserFilled } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()
const themeStore = useThemeStore()

/** 可选主题列表 */
const themeOptions = [
  { label: '浅色模式', value: 'light', color: '#f0f2f5' },
  { label: '深色模式', value: 'dark', color: '#1d1e1f' },
  { label: '蓝色主题', value: 'blue', color: '#2563eb' },
  { label: '绿色主题', value: 'green', color: '#059669' },
  { label: '紫色主题', value: 'purple', color: '#7c3aed' },
]

/** 切换主题 */
function handleThemeChange(theme) {
  themeStore.setTheme(theme)
}

/** 用户下拉操作 */
async function handleCommand(command) {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
      await userStore.logout()
      router.push('/login')
    } catch {
      // 取消
    }
  } else if (command === 'profile') {
    // TODO: 个人中心
  }
}
</script>

<style scoped>
.navbar {
  height: var(--navbar-height);
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: var(--bg-navbar);
  border-bottom: 1px solid var(--border-color);
  padding: 0 20px;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.collapse-btn {
  font-size: 20px;
  cursor: pointer;
  color: var(--text-secondary);
  transition: color 0.2s;
}

.collapse-btn:hover {
  color: var(--color-primary);
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* ---- 主题切换 ---- */
.theme-trigger {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 8px;
  cursor: pointer;
  color: var(--text-secondary);
  transition: all 0.2s;
}

.theme-trigger:hover {
  background: var(--bg-hover);
  color: var(--color-primary);
}

.theme-dot {
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin-right: 8px;
  border: 1px solid var(--border-color-dark);
  flex-shrink: 0;
}

.check-icon {
  margin-left: auto;
  color: var(--color-primary);
}

/* ---- 用户信息 ---- */
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: var(--text-primary);
  padding: 4px 8px;
  border-radius: 8px;
  transition: background-color 0.2s;
}

.user-info:hover {
  background: var(--bg-hover);
}

.username {
  font-size: 14px;
  max-width: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
