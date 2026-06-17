package com.example.ccdemo.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录结果 VO
 *
 * @author ccDemo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultVO {

    /** 访问 Token */
    private String accessToken;

    /** 刷新 Token */
    private String refreshToken;

    /** Token 类型 */
    private String tokenType;

    /** 过期时间（秒） */
    private Long expiresIn;

    /** 用户 ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;
}
