import request from '../request'

/** 分页查询角色 */
export function pageRoles(params) {
  return request.get('/role/page', { params })
}

/** 获取所有角色 */
export function listRoles() {
  return request.get('/role/list')
}

/** 获取单个角色 */
export function getRole(id) {
  return request.get(`/role/${id}`)
}

/** 新增角色 */
export function addRole(data) {
  return request.post('/role', data)
}

/** 编辑角色 */
export function updateRole(data) {
  return request.put('/role', data)
}

/** 删除角色 */
export function deleteRole(id) {
  return request.delete(`/role/${id}`)
}

/** 分配菜单 */
export function assignMenus(roleId, menuIds) {
  return request.put(`/role/${roleId}/menus`, menuIds)
}

/** 获取角色菜单 */
export function getRoleMenus(roleId) {
  return request.get(`/role/${roleId}/menus`)
}
