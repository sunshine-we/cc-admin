<template>
  <div class="error-page">
    <div class="error-code">404</div>
    <p class="error-desc">抱歉，您访问的页面不存在</p>
    <el-button type="primary" @click="$router.push('/dashboard')">返回首页</el-button>
  </div>
</template>

<style scoped>
.error-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;
  background: var(--bg-page);
  transition: background-color 0.3s ease;
}

.error-code {
  font-size: 96px;
  font-weight: 800;
  color: var(--color-warning);
  line-height: 1;
  letter-spacing: 4px;
  transition: color 0.3s ease;
}

.error-desc {
  font-size: 18px;
  color: var(--text-secondary);
  transition: color 0.3s ease;
}
</style>
