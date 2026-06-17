/**
 * Token 管理工具
 */

const ACCESS_TOKEN_KEY = 'ccdemo_access_token'
const REFRESH_TOKEN_KEY = 'ccdemo_refresh_token'
const USER_KEY = 'ccdemo_user'

/** 获取 Access Token */
export function getToken() {
  return localStorage.getItem(ACCESS_TOKEN_KEY)
}

/** 设置 Access Token */
export function setToken(token) {
  localStorage.setItem(ACCESS_TOKEN_KEY, token)
}

/** 移除 Access Token */
export function removeToken() {
  localStorage.removeItem(ACCESS_TOKEN_KEY)
}

/** 获取 Refresh Token */
export function getRefreshToken() {
  return localStorage.getItem(REFRESH_TOKEN_KEY)
}

/** 设置 Refresh Token */
export function setRefreshToken(token) {
  localStorage.setItem(REFRESH_TOKEN_KEY, token)
}

/** 移除 Refresh Token */
export function removeRefreshToken() {
  localStorage.removeItem(REFRESH_TOKEN_KEY)
}

/** 获取用户信息 */
export function getUser() {
  const user = localStorage.getItem(USER_KEY)
  return user ? JSON.parse(user) : null
}

/** 设置用户信息 */
export function setUser(user) {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

/** 移除用户信息 */
export function removeUser() {
  localStorage.removeItem(USER_KEY)
}

/** 清除所有登录信息 */
export function clearAuth() {
  removeToken()
  removeRefreshToken()
  removeUser()
}

/** 是否已登录 */
export function isLoggedIn() {
  return !!getToken()
}
