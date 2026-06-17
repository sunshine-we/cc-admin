package com.example.ccdemo.common.jwt;

import com.example.ccdemo.common.annotation.IgnoreAuth;
import com.example.ccdemo.common.result.Result;
import com.example.ccdemo.common.result.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * JWT 认证拦截器
 *
 * @author ccDemo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 非 Controller 方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 方法或类上有 @IgnoreAuth 注解则跳过认证
        if (handlerMethod.getMethodAnnotation(IgnoreAuth.class) != null ||
                handlerMethod.getBeanType().getAnnotation(IgnoreAuth.class) != null) {
            return true;
        }

        // 从 Header 中获取 Token
        String token = extractToken(request);
        if (token == null || token.isEmpty()) {
            writeUnauthorized(response, "请先登录");
            return false;
        }

        // Token 已过期
        if (jwtUtil.isTokenExpired(token)) {
            writeUnauthorized(response, "Token 已过期，请重新登录");
            return false;
        }

        // Token 无效
        if (!jwtUtil.isTokenValid(token)) {
            writeUnauthorized(response, "Token 无效");
            return false;
        }

        // 将用户信息存入上下文
        JwtUserContext.setUserId(jwtUtil.getUserId(token));
        JwtUserContext.setUsername(jwtUtil.getUsername(token));

        return true;
    }

    /**
     * 从请求头中提取 Token
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 返回未授权响应
     */
    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        Result<Void> result = Result.fail(ResultCode.UNAUTHORIZED.getCode(), message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求完成后清除 ThreadLocal，防止内存泄漏
        JwtUserContext.clear();
    }
}
