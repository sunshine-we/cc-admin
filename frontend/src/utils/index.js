/**
 * 通用工具函数
 */

/** 格式化日期（yyyy-MM-dd HH:mm:ss） */
export function formatDateTime(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const pad = (n) => String(n).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
}

/** 格式化日期（yyyy-MM-dd） */
export function formatDate(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const pad = (n) => String(n).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`
}

/** 树形数据转扁平 */
export function treeToList(tree, children = 'children') {
  const result = []
  const traverse = (nodes) => {
    nodes.forEach((node) => {
      const copy = { ...node }
      delete copy[children]
      result.push(copy)
      if (node[children] && node[children].length > 0) {
        traverse(node[children])
      }
    })
  }
  traverse(tree)
  return result
}
