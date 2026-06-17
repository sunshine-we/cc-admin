import request from '../request'

/** 用户登录 */
export function login(username, password) {
  return request.post('/auth/login', { username, password })
}

/** 用户注册 */
export function register(data) {
  return request.post('/auth/register', data)
}

/** 刷新 Token */
export function refreshToken(refreshToken) {
  return request.post('/auth/refresh-token', null, {
    params: { refreshToken }
  })
}

/** 获取当前用户信息 */
export function getCurrentUser() {
  return request.get('/auth/current-user')
}

/** 退出登录 */
export function logout() {
  return request.post('/auth/logout')
}
