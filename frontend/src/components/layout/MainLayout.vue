<template>
  <el-container class="main-layout">
    <!-- 侧边栏 -->
    <Sidebar />
    <el-container class="main-container">
      <!-- 顶部导航栏 -->
      <Navbar />
      <!-- 内容区 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Navbar from './Navbar.vue'
</script>

<style scoped>
.main-layout {
  height: 100vh;
  overflow: hidden;
}

.main-container {
  flex-direction: column;
  overflow: hidden;
}

.main-content {
  background: var(--bg-page);
  padding: 20px;
  overflow-y: auto;
  transition: background-color 0.3s ease;
}
</style>
