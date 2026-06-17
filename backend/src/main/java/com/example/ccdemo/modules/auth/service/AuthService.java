package com.example.ccdemo.modules.auth.service;

import com.example.ccdemo.modules.auth.dto.LoginDTO;
import com.example.ccdemo.modules.auth.dto.LoginResultVO;
import com.example.ccdemo.modules.auth.dto.RegisterDTO;
import com.example.ccdemo.modules.user.entity.User;

/**
 * 认证服务接口
 *
 * @author ccDemo
 */
public interface AuthService {

    /**
     * 用户登录
     */
    LoginResultVO login(LoginDTO loginDTO);

    /**
     * 用户注册
     */
    void register(RegisterDTO registerDTO);

    /**
     * 刷新 Token
     */
    LoginResultVO refreshToken(String refreshToken);

    /**
     * 获取当前登录用户信息
     */
    User getCurrentUser();
}
