import { defineStore } from 'pinia'
import { ref, watch } from 'vue'

/**
 * 主题管理 Store
 *
 * 支持主题模式:
 *  - light:  浅色模式（默认）
 *  - dark:   深色模式
 *  - blue:   蓝色主题
 *  - green:  绿色主题
 *  - purple: 紫色主题
 */
const THEME_KEY = 'cc-admin-theme'
const VALID_THEMES = ['light', 'dark', 'blue', 'green', 'purple']

/** 读取 localStorage 中的主题设置 */
function getStoredTheme() {
  try {
    const stored = localStorage.getItem(THEME_KEY)
    if (stored && VALID_THEMES.includes(stored)) {
      return stored
    }
  } catch {
    // localStorage 不可用时忽略
  }
  return 'light'
}

/** 持久化主题到 localStorage */
function persistTheme(theme) {
  try {
    localStorage.setItem(THEME_KEY, theme)
  } catch {
    // 忽略
  }
}

export const useThemeStore = defineStore('theme', () => {
  // 当前主题
  const currentTheme = ref(getStoredTheme())

  // 是否为深色模式（用于 Element Plus 暗黑模式切换）
  const isDark = ref(currentTheme.value === 'dark')

  // 应用主题到 DOM
  function applyTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme)

    // Element Plus 暗黑模式
    if (theme === 'dark') {
      document.documentElement.classList.add('dark')
    } else {
      document.documentElement.classList.remove('dark')
    }

    isDark.value = theme === 'dark'
  }

  // 切换主题
  function setTheme(theme) {
    if (!VALID_THEMES.includes(theme)) {
      console.warn(`无效的主题: ${theme}，已回退到 light`)
      theme = 'light'
    }
    currentTheme.value = theme
    persistTheme(theme)
    applyTheme(theme)
  }

  // 初始化主题
  applyTheme(currentTheme.value)

  return {
    currentTheme,
    isDark,
    setTheme,
    VALID_THEMES,
  }
})
