<template>
  <div class="dashboard">
    <h3>欢迎使用 ccDemo 管理后台</h3>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span>系统信息</span>
          </template>
          <p><strong>系统名称：</strong>ccDemo 脚手架</p>
          <p><strong>版本：</strong>1.0.0</p>
          <p><strong>前端框架：</strong>Vue 3 + Element Plus</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span>技术栈</span>
          </template>
          <p><strong>后端：</strong>Spring Boot 3.x</p>
          <p><strong>ORM：</strong>MyBatis Plus</p>
          <p><strong>数据库：</strong>MySQL 8.x</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span>快捷入口</span>
          </template>
          <p><el-link type="primary" href="/system/user">用户管理</el-link></p>
          <p><el-link type="primary" href="/system/role">角色管理</el-link></p>
          <p><el-link type="primary" href="/system/menu">菜单管理</el-link></p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
</script>

<style scoped>
.dashboard h3 {
  color: #333;
}

.el-card p {
  margin: 8px 0;
  color: #666;
  font-size: 14px;
}
</style>
