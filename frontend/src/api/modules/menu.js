import request from '../request'

/** 获取全部菜单树 */
export function getMenuTree() {
  return request.get('/menu/tree')
}

/** 获取当前用户菜单 */
export function getUserMenu(userId) {
  return request.get('/menu/user-menu', { params: { userId } })
}

/** 获取单个菜单 */
export function getMenu(id) {
  return request.get(`/menu/${id}`)
}

/** 新增菜单 */
export function addMenu(data) {
  return request.post('/menu', data)
}

/** 编辑菜单 */
export function updateMenu(data) {
  return request.put('/menu', data)
}

/** 删除菜单 */
export function deleteMenu(id) {
  return request.delete(`/menu/${id}`)
}
