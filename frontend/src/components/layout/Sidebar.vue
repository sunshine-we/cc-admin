<template>
  <el-aside :width="isCollapsed ? '64px' : '220px'" class="sidebar">
    <!-- Logo 区域 -->
    <div class="logo">
      <span class="logo-text" v-if="!isCollapsed">ccAdmin</span>
      <span class="logo-text" v-else>CA</span>
    </div>

    <!-- 菜单 -->
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapsed"
      :collapse-transition="false"
      router
      class="sidebar-menu"
    >
      <template v-for="item in menuList" :key="item.id">
        <!-- 有子菜单 -->
        <el-sub-menu v-if="item.children && item.children.length > 0" :index="item.path">
          <template #title>
            <el-icon><component :is="item.icon" /></el-icon>
            <span>{{ item.menuName }}</span>
          </template>
          <el-menu-item
            v-for="child in item.children"
            :key="child.id"
            :index="child.path"
          >
            <el-icon><component :is="child.icon" /></el-icon>
            <span>{{ child.menuName }}</span>
          </el-menu-item>
        </el-sub-menu>

        <!-- 无子菜单 -->
        <el-menu-item v-else :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.menuName }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </el-aside>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'
import { getUserMenu } from '@/api/modules/menu'

const route = useRoute()
const appStore = useAppStore()
const userStore = useUserStore()

const isCollapsed = computed(() => appStore.sidebarCollapsed)
const activeMenu = computed(() => route.path)

const menuList = ref([])

onMounted(async () => {
  try {
    const userId = userStore.userInfo?.userId || userStore.userInfo?.id
    if (userId) {
      const res = await getUserMenu(userId)
      menuList.value = res.data || []
    }
  } catch {
    menuList.value = getDefaultMenus()
  }

  if (menuList.value.length === 0) {
    menuList.value = getDefaultMenus()
  }
})

/** 默认菜单（后端未返回时使用） */
function getDefaultMenus() {
  return [
    {
      id: 1,
      menuName: '仪表盘',
      icon: 'DataBoard',
      path: '/dashboard',
    },
    {
      id: 2,
      menuName: '系统管理',
      icon: 'Setting',
      children: [
        { id: 21, menuName: '用户管理', icon: 'User', path: '/system/user' },
        { id: 22, menuName: '角色管理', icon: 'Avatar', path: '/system/role' },
        { id: 23, menuName: '菜单管理', icon: 'Menu', path: '/system/menu' },
      ],
    },
  ]
}
</script>

<style scoped>
.sidebar {
  background-color: var(--bg-sidebar);
  overflow-y: auto;
  overflow-x: hidden;
  transition: width 0.3s ease, background-color 0.3s ease;
}

/* ---- Logo ---- */
.logo {
  height: var(--navbar-height);
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--sidebar-logo-bg);
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  transition: background-color 0.3s ease;
}

.logo-text {
  color: var(--text-sidebar-active);
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 2px;
  white-space: nowrap;
  transition: all 0.3s ease;
}

/* ---- 菜单 ---- */
.sidebar-menu {
  border-right: none;
  background-color: transparent;
}

/* 菜单项 - 使用 CSS 变量控制颜色 */
.sidebar-menu :deep(.el-menu-item),
.sidebar-menu :deep(.el-sub-menu__title) {
  color: var(--text-sidebar);
  transition: all 0.2s ease;
}

.sidebar-menu :deep(.el-menu-item:hover),
.sidebar-menu :deep(.el-sub-menu__title:hover) {
  background-color: var(--bg-sidebar-hover);
  color: var(--text-sidebar-active);
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  color: var(--text-sidebar-active);
  background-color: var(--color-primary);
}

/* 子菜单背景 */
.sidebar-menu :deep(.el-menu) {
  background-color: rgba(0, 0, 0, 0.1);
}

/* 折叠状态微调 */
.sidebar-menu.el-menu--collapse :deep(.el-sub-menu__title) {
  display: flex;
  justify-content: center;
}
</style>
