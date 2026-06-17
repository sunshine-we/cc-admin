package com.example.ccdemo.modules.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ccdemo.common.result.Result;
import com.example.ccdemo.modules.user.entity.User;
import com.example.ccdemo.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 *
 * @author ccDemo
 */
@Tag(name = "用户管理", description = "用户 CRUD、角色分配")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    public Result<IPage<User>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "用户名（模糊搜索）") @RequestParam(required = false) String username,
            @Parameter(description = "昵称（模糊搜索）") @RequestParam(required = false) String nickname) {
        Page<User> page = new Page<>(current, size);
        IPage<User> result = userService.pageUsers(page, username, nickname);
        return Result.ok(result);
    }

    @Operation(summary = "根据 ID 查询用户")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return Result.ok(user);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public Result<String>add(@RequestBody User user) {
        userService.addUser(user);
        return Result.ok("新增成功");
    }

    @Operation(summary = "编辑用户")
    @PutMapping
    public Result<String>update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.ok("编辑成功");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<String>delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok("删除成功");
    }

    @Operation(summary = "分配角色")
    @PutMapping("/{userId}/roles")
    public Result<String>assignRoles(
            @PathVariable Long userId,
            @RequestBody List<Long> roleIds) {
        userService.assignRoles(userId, roleIds);
        return Result.ok("角色分配成功");
    }

    @Operation(summary = "获取用户角色 ID 列表")
    @GetMapping("/{userId}/roles")
    public Result<List<Long>> getUserRoles(@PathVariable Long userId) {
        List<Long> roleIds = userService.getUserRoleIds(userId);
        return Result.ok(roleIds);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/reset-password")
    public Result<String>resetPassword(
            @PathVariable Long id,
            @RequestParam String password) {
        userService.resetPassword(id, password);
        return Result.ok("密码重置成功");
    }
}
