package com.example.ccdemo.modules.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ccdemo.common.result.Result;
import com.example.ccdemo.modules.role.entity.Role;
import com.example.ccdemo.modules.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 *
 * @author ccDemo
 */
@Tag(name = "角色管理", description = "角色 CRUD、菜单权限分配")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    public Result<IPage<Role>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "角色名称") @RequestParam(required = false) String roleName) {
        Page<Role> page = new Page<>(current, size);
        return Result.ok(roleService.pageRoles(page, roleName));
    }

    @Operation(summary = "获取所有角色（下拉列表用）")
    @GetMapping("/list")
    public Result<List<Role>> list() {
        return Result.ok(roleService.list());
    }

    @Operation(summary = "根据 ID 查询角色")
    @GetMapping("/{id}")
    public Result<Role> getById(@PathVariable Long id) {
        return Result.ok(roleService.getById(id));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public Result<String>add(@RequestBody Role role) {
        roleService.addRole(role);
        return Result.ok("新增成功");
    }

    @Operation(summary = "编辑角色")
    @PutMapping
    public Result<String>update(@RequestBody Role role) {
        roleService.updateRole(role);
        return Result.ok("编辑成功");
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<String>delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.ok("删除成功");
    }

    @Operation(summary = "分配菜单权限")
    @PutMapping("/{roleId}/menus")
    public Result<String>assignMenus(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds) {
        roleService.assignMenus(roleId, menuIds);
        return Result.ok("菜单权限分配成功");
    }

    @Operation(summary = "获取角色菜单 ID 列表")
    @GetMapping("/{roleId}/menus")
    public Result<List<Long>> getRoleMenus(@PathVariable Long roleId) {
        return Result.ok(roleService.getRoleMenuIds(roleId));
    }
}
