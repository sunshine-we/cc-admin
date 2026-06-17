package com.example.ccdemo.modules.auth.controller;

import com.example.ccdemo.common.annotation.IgnoreAuth;
import com.example.ccdemo.common.result.Result;
import com.example.ccdemo.modules.auth.dto.LoginDTO;
import com.example.ccdemo.modules.auth.dto.LoginResultVO;
import com.example.ccdemo.modules.auth.dto.RegisterDTO;
import com.example.ccdemo.modules.auth.service.AuthService;
import com.example.ccdemo.modules.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author ccDemo
 */
@Tag(name = "认证管理", description = "登录、注册、Token 刷新")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录")
    @IgnoreAuth
    @PostMapping("/login")
    public Result<LoginResultVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResultVO result = authService.login(loginDTO);
        return Result.ok("登录成功", result);
    }

    @Operation(summary = "用户注册")
    @IgnoreAuth
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return Result.ok("注册成功");
    }

    @Operation(summary = "刷新 Token")
    @IgnoreAuth
    @PostMapping("/refresh-token")
    public Result<LoginResultVO> refreshToken(@RequestParam String refreshToken) {
        LoginResultVO result = authService.refreshToken(refreshToken);
        return Result.ok(result);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/current-user")
    public Result<User> getCurrentUser() {
        User user = authService.getCurrentUser();
        return Result.ok(user);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        // 无状态 JWT 退出由前端清除 Token 即可
        return Result.ok("退出成功");
    }
}
