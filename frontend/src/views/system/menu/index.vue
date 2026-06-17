<template>
  <div class="page-container">
    <el-card shadow="never" class="table-card">
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd(0)">新增顶级菜单</el-button>
      </div>

      <el-table
        :data="menuTree"
        row-key="id"
        border
        stripe
        v-loading="loading"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column prop="menuName" label="菜单名称" min-width="200" />
        <el-table-column prop="menuType" label="菜单类型" width="100">
          <template #default="{ row }">
            <el-tag :type="typeTag(row.menuType)">
              {{ typeLabel(row.menuType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="icon" label="图标" width="80" />
        <el-table-column prop="path" label="路由路径" width="180" />
        <el-table-column prop="component" label="组件路径" width="200" />
        <el-table-column prop="permission" label="权限标识" width="180" />
        <el-table-column prop="sort" label="排序" width="60" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '显示' : '隐藏' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleAdd(row.id)" v-if="row.menuType !== 'button'">新增子菜单</el-button>
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑菜单' : '新增菜单'"
      width="550px"
      @close="resetForm"
    >
      <el-form ref="formRef" :model="menuForm" :rules="menuRules" label-width="90px">
        <el-form-item label="上级菜单">
          <el-tree-select
            v-model="menuForm.parentId"
            :data="parentTreeData"
            :props="{ label: 'menuName', children: 'children' }"
            value-key="id"
            placeholder="无（顶级菜单）"
            clearable
            check-strictly
          />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-select v-model="menuForm.menuType" style="width: 100%">
            <el-option label="目录" value="catalog" />
            <el-option label="菜单" value="menu" />
            <el-option label="按钮" value="button" />
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="menuForm.menuName" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="路由路径" v-if="menuForm.menuType !== 'button'">
          <el-input v-model="menuForm.path" placeholder="请输入路由路径" />
        </el-form-item>
        <el-form-item label="组件路径" v-if="menuForm.menuType === 'menu'">
          <el-input v-model="menuForm.component" placeholder="请输入组件路径" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="menuForm.icon" placeholder="请输入 Element Plus 图标名" />
        </el-form-item>
        <el-form-item label="权限标识" v-if="menuForm.menuType !== 'catalog'">
          <el-input v-model="menuForm.permission" placeholder="例如 sys:user:add" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="menuForm.sort" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="menuForm.status">
            <el-radio :value="1">显示</el-radio>
            <el-radio :value="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMenuTree, addMenu, updateMenu, deleteMenu, getMenu } from '@/api/modules/menu'
import { ElMessage, ElMessageBox } from 'element-plus'

const menuTree = ref([])
const parentTreeData = ref([])
const loading = ref(false)

async function loadData() {
  loading.value = true
  try {
    const res = await getMenuTree()
    menuTree.value = res.data || []
    // 父级菜单选择数据
    parentTreeData.value = [{ id: 0, menuName: '顶级菜单', children: res.data || [] }]
  } finally { loading.value = false }
}

function typeLabel(type) {
  return { catalog: '目录', menu: '菜单', button: '按钮' }[type] || type
}

function typeTag(type) {
  return { catalog: '', menu: 'success', button: 'warning' }[type] || 'info'
}

// 新增/编辑
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const editId = ref(null)

const menuForm = reactive({
  parentId: 0,
  menuName: '',
  menuType: 'menu',
  path: '',
  component: '',
  icon: '',
  permission: '',
  sort: 0,
  status: 1
})

const menuRules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuType: [{ required: true, message: '请选择菜单类型', trigger: 'change' }]
}

function handleAdd(parentId) {
  isEdit.value = false
  resetForm()
  menuForm.parentId = parentId
  dialogVisible.value = true
}

async function handleEdit(row) {
  isEdit.value = true
  editId.value = row.id
  // 获取最新数据
  const res = await getMenu(row.id)
  const data = res.data
  Object.assign(menuForm, {
    parentId: data.parentId,
    menuName: data.menuName,
    menuType: data.menuType,
    path: data.path || '',
    component: data.component || '',
    icon: data.icon || '',
    permission: data.permission || '',
    sort: data.sort,
    status: data.status
  })
  dialogVisible.value = true
}

function resetForm() {
  Object.assign(menuForm, {
    parentId: 0,
    menuName: '',
    menuType: 'menu',
    path: '',
    component: '',
    icon: '',
    permission: '',
    sort: 0,
    status: 1
  })
  editId.value = null
  formRef.value?.resetFields()
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) {
    await updateMenu({ id: editId.value, ...menuForm })
    ElMessage.success('编辑成功')
  } else {
    await addMenu(menuForm)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  loadData()
}

async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('删除该菜单将同时删除其子菜单，确定继续？', '提示', { type: 'warning' })
    await deleteMenu(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) { /* 取消 */ }
}

onMounted(() => { loadData() })
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.toolbar { margin-bottom: 16px; }
</style>
