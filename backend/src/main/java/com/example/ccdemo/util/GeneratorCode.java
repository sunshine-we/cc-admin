package com.example.ccdemo.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * MyBatis Plus 代码生成器
 * <p>
 * 使用方式：修改下方数据库连接参数和表名，运行 main 方法即可。
 *
 * @author ccDemo
 */
public class GeneratorCode {

    /**
     * 数据库连接 URL
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ccdemo?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";

    /**
     * 数据库用户名
     */
    private static final String DB_USERNAME = "root";

    /**
     * 数据库密码
     */
    private static final String DB_PASSWORD = "root";

    /**
     * 作者名（生成在 @author 注释中）
     */
    private static final String AUTHOR = "ccDemo";

    /**
     * 基础包名
     */
    private static final String BASE_PACKAGE = "com.example.ccdemo";

    /**
     * 模块名（生成的代码会放在 modules/{moduleName} 下）
     */
    private static final String MODULE_NAME = "biz";

    /**
     * 要生成的表名（多个用逗号分隔）
     */
    private static final String TABLE_NAMES = "example_table";

    /**
     * 表前缀（生成实体类名时会去掉此前缀）
     */
    private static final String TABLE_PREFIX = "";

    /**
     * 输出目录
     */
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/java";

    /**
     * Mapper XML 输出目录
     */
    private static final String MAPPER_XML_DIR = System.getProperty("user.dir") + "/src/main/resources/mapper";

    public static void main(String[] args) {
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                // ==================== 全局配置 ====================
                .globalConfig(builder -> builder
                        .author(AUTHOR)
                        .outputDir(OUTPUT_DIR)
                        .commentDate("yyyy-MM-dd")
                        .disableOpenDir()
                )
                // ==================== 包配置 ====================
                .packageConfig(builder -> builder
                        .parent(BASE_PACKAGE)
                        .moduleName(MODULE_NAME)
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_DIR))
                )
                // ==================== 策略配置 ====================
                .strategyConfig(builder -> builder
                        .addInclude(TABLE_NAMES.split(","))
                        // 表前缀
                        .addTablePrefix(TABLE_PREFIX)
                        // Entity 策略
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .enableRemoveIsPrefix()
                        // Mapper 策略
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        // Service 策略
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        // Controller 策略
                        .controllerBuilder()
                        .enableRestStyle()
                        .enableHyphenStyle()
                )
                // ==================== 数据库类型转换 ====================
                .dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.TINYINT || typeCode == Types.SMALLINT) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                // ==================== 模板引擎 ====================
                .templateEngine(new VelocityTemplateEngine())
                .execute();

        System.out.println("========================================");
        System.out.println("  代码生成完毕！");
        System.out.println("  实体类: " + OUTPUT_DIR + "/" + BASE_PACKAGE.replace('.', '/') + "/" + MODULE_NAME + "/entity");
        System.out.println("  Mapper: " + OUTPUT_DIR + "/" + BASE_PACKAGE.replace('.', '/') + "/" + MODULE_NAME + "/mapper");
        System.out.println("  Service: " + OUTPUT_DIR + "/" + BASE_PACKAGE.replace('.', '/') + "/" + MODULE_NAME + "/service");
        System.out.println("  Controller: " + OUTPUT_DIR + "/" + BASE_PACKAGE.replace('.', '/') + "/" + MODULE_NAME + "/controller");
        System.out.println("  XML: " + MAPPER_XML_DIR);
        System.out.println("========================================");
    }
}
