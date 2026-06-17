package com.example.ccdemo.modules.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.ccdemo.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单实体
 *
 * @author ccDemo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("menu")
public class Menu extends BaseEntity {

    /** 父菜单 ID（0=顶级菜单） */
    private Long parentId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单类型（catalog=目录，menu=菜单，button=按钮） */
    private String menuType;

    /** 路由路径 */
    private String path;

    /** 前端组件路径 */
    private String component;

    /** 菜单图标 */
    private String icon;

    /** 权限标识（如 sys:user:add） */
    private String permission;

    /** 排序 */
    private Integer sort;

    /** 状态（0=隐藏，1=显示） */
    private Integer status;

    /** 子菜单列表（非数据库字段） */
    @TableField(exist = false)
    private List<Menu> children;
}
