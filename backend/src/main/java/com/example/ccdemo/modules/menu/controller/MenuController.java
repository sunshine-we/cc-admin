package com.example.ccdemo.modules.menu.controller;

import com.example.ccdemo.common.result.Result;
import com.example.ccdemo.modules.menu.entity.Menu;
import com.example.ccdemo.modules.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author ccDemo
 */
@Tag(name = "菜单管理", description = "菜单树管理、权限分配")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取菜单树（全部）")
    @GetMapping("/tree")
    public Result<List<Menu>> getMenuTree() {
        return Result.ok(menuService.getMenuTree());
    }

    @Operation(summary = "获取当前用户菜单")
    @GetMapping("/user-menu")
    public Result<List<Menu>> getCurrentUserMenus(@RequestParam Long userId) {
        return Result.ok(menuService.getCurrentUserMenus(userId));
    }

    @Operation(summary = "查询单个菜单")
    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.ok(menuService.getById(id));
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    public Result<String>add(@RequestBody Menu menu) {
        menuService.addMenu(menu);
        return Result.ok("新增成功");
    }

    @Operation(summary = "编辑菜单")
    @PutMapping
    public Result<String>update(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return Result.ok("编辑成功");
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<String>delete(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return Result.ok("删除成功");
    }
}
