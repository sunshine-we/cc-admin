import request from '../request'

/** 分页查询用户 */
export function pageUsers(params) {
  return request.get('/user/page', { params })
}

/** 获取单个用户 */
export function getUser(id) {
  return request.get(`/user/${id}`)
}

/** 新增用户 */
export function addUser(data) {
  return request.post('/user', data)
}

/** 编辑用户 */
export function updateUser(data) {
  return request.put('/user', data)
}

/** 删除用户 */
export function deleteUser(id) {
  return request.delete(`/user/${id}`)
}

/** 分配角色 */
export function assignRoles(userId, roleIds) {
  return request.put(`/user/${userId}/roles`, roleIds)
}

/** 获取用户角色 */
export function getUserRoles(userId) {
  return request.get(`/user/${userId}/roles`)
}

/** 重置密码 */
export function resetPassword(id, password) {
  return request.put(`/user/${id}/reset-password`, null, {
    params: { password }
  })
}
