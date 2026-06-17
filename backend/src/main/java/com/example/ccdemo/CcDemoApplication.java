package com.example.ccdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ccDemo 启动类
 *
 * @author ccDemo
 */
@SpringBootApplication
@MapperScan("com.example.ccdemo.modules.*.mapper")
public class CcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcDemoApplication.class, args);
        System.out.println("========================================");
        System.out.println("  ccDemo 启动成功！");
        System.out.println("  API 文档: http://localhost:8080/doc.html");
        System.out.println("========================================");
    }
}
