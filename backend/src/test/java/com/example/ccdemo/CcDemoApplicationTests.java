package com.example.ccdemo;

import com.example.ccdemo.common.jwt.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 基础单元测试
 *
 * @author ccDemo
 */
@SpringBootTest
class CcDemoApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        // 验证 Spring 上下文加载成功
    }

    @Test
    void testPasswordEncode() {
        String rawPassword = "123456";
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("BCrypt 密文: " + encoded);
        Assertions.assertTrue(passwordEncoder.matches(rawPassword, encoded));
    }

    @Test
    void testJwtGenerateAndParse() {
        String token = jwtUtil.generateToken(1L, "admin");
        System.out.println("JWT Token: " + token);
        Assertions.assertNotNull(token);

        Assertions.assertTrue(jwtUtil.isTokenValid(token));
        Assertions.assertEquals(1L, jwtUtil.getUserId(token));
        Assertions.assertEquals("admin", jwtUtil.getUsername(token));
    }

    @Test
    void testJwtExpired() {
        String token = jwtUtil.generateToken(1L, "admin");
        // Token 刚生成应该未过期
        Assertions.assertFalse(jwtUtil.isTokenExpired(token));
    }
}
