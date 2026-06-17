import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, logout as logoutApi, getCurrentUser } from '@/api/modules/auth'
import { setToken, setRefreshToken, getToken, getRefreshToken, setUser, getUser, clearAuth } from '@/utils/auth'

export const useUserStore = defineStore('user', () => {
  // 状态
  const token = ref(getToken() || '')
  const refreshToken = ref(getRefreshToken() || '')
  const userInfo = ref(getUser() || {})
  const roles = ref([])
  const permissions = ref([])

  // 登录
  async function login(username, password) {
    const res = await loginApi(username, password)
    const { accessToken, refreshToken: rToken, ...user } = res.data

    token.value = accessToken
    refreshToken.value = rToken
    userInfo.value = user

    setToken(accessToken)
    setRefreshToken(rToken)
    setUser(user)

    return user
  }

  // 获取用户信息
  async function fetchCurrentUser() {
    const res = await getCurrentUser()
    userInfo.value = res.data
    setUser(res.data)
    return res.data
  }

  // 退出登录
  async function logout() {
    try {
      await logoutApi()
    } catch (e) {
      // 即使请求失败也清除本地信息
    } finally {
      token.value = ''
      refreshToken.value = ''
      userInfo.value = {}
      clearAuth()
    }
  }

  // 是否已登录
  function isLoggedIn() {
    return !!token.value
  }

  return {
    token,
    refreshToken,
    userInfo,
    roles,
    permissions,
    login,
    fetchCurrentUser,
    logout,
    isLoggedIn
  }
})
