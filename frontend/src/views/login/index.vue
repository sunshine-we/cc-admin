<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="title">ccAdmin 管理后台</h2>
      <p class="subtitle">一个开箱即用的后台管理脚手架</p>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        size="large"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            :prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            style="width: 100%"
            @click="handleLogin"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>

      <p class="tip">默认账号：admin / 123456</p>

      <!-- 底部主题切换 -->
      <div class="login-theme-switcher">
        <span
          v-for="t in themes"
          :key="t.value"
          class="theme-btn"
          :class="{ active: themeStore.currentTheme === t.value }"
          :style="{ '--dot-color': t.color }"
          :title="t.label"
          @click="themeStore.setTheme(t.value)"
        >
          <span class="dot"></span>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useThemeStore } from '@/stores/theme'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const themeStore = useThemeStore()

const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456',
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

const themes = [
  { label: '浅色', value: 'light', color: '#409eff' },
  { label: '深色', value: 'dark', color: '#303133' },
  { label: '蓝色', value: 'blue', color: '#2563eb' },
  { label: '绿色', value: 'green', color: '#059669' },
  { label: '紫色', value: 'purple', color: '#7c3aed' },
]

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.login(form.username, form.password)
    ElMessage.success('登录成功')
    const redirect = route.query.redirect || '/dashboard'
    router.push(redirect)
  } catch {
    // 错误信息已在拦截器中处理
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  transition: background 0.5s ease;
}

/* 深色模式下调整登录页背景 */
[data-theme='dark'] .login-container {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
}

.login-card {
  width: 420px;
  padding: 44px 40px 36px;
  background: var(--bg-container);
  border-radius: 12px;
  box-shadow: var(--shadow-lg);
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.title {
  text-align: center;
  color: var(--text-primary);
  margin-bottom: 8px;
  font-size: 26px;
  font-weight: 700;
  transition: color 0.3s ease;
}

.subtitle {
  text-align: center;
  color: var(--text-secondary);
  font-size: 13px;
  margin-bottom: 28px;
}

.tip {
  text-align: center;
  color: var(--text-secondary);
  font-size: 13px;
  margin-top: 12px;
  transition: color 0.3s ease;
}

/* ---- 登录页主题切换 ---- */
.login-theme-switcher {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid var(--border-color-light);
}

.theme-btn {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid transparent;
  transition: all 0.2s;
  background: var(--bg-hover);
}

.theme-btn:hover {
  border-color: var(--color-primary);
  transform: scale(1.15);
}

.theme-btn.active {
  border-color: var(--dot-color);
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: var(--dot-color);
}
</style>
