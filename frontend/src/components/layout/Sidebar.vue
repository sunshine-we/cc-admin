<template>
  <el-aside :width="isCollapsed ? '64px' : '220px'" class="sidebar">
    <div class="logo">
      <span v-if="!isCollapsed">ccDemo</span>
      <span v-else>CD</span>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapsed"
      :collapse-transition="false"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      router
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
  } catch (e) {
    // 菜单加载失败使用默认菜单
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
      path: '/dashboard'
    },
    {
      id: 2,
      menuName: '系统管理',
      icon: 'Setting',
      children: [
        { id: 21, menuName: '用户管理', icon: 'User', path: '/system/user' },
        { id: 22, menuName: '角色管理', icon: 'Avatar', path: '/system/role' },
        { id: 23, menuName: '菜单管理', icon: 'Menu', path: '/system/menu' }
      ]
    }
  ]
}
</script>

<style scoped>
.sidebar {
  background-color: #304156;
  overflow-y: auto;
  overflow-x: hidden;
  transition: width 0.3s;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 2px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.el-menu {
  border-right: none;
}
</style>
