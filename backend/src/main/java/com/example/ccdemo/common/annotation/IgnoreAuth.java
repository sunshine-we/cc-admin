package com.example.ccdemo.common.annotation;

import java.lang.annotation.*;

/**
 * 跳过 JWT 认证注解
 * 标记在方法上表示该接口不需要 Token 验证（如登录、注册）
 *
 * @author ccDemo
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
