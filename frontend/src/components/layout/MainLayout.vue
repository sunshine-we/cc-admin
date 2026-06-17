<template>
  <el-container class="main-layout">
    <!-- 侧边栏 -->
    <Sidebar />
    <el-container>
      <!-- 顶部导航栏 -->
      <Navbar />
      <!-- 内容区 -->
      <el-main>
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
}

.el-main {
  background: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}
</style>
