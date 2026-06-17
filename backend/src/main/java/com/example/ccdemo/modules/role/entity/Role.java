package com.example.ccdemo.modules.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.ccdemo.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 *
 * @author ccDemo
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("role")
public class Role extends BaseEntity {

    /** 角色名称 */
    private String roleName;

    /** 角色编码 */
    private String roleCode;

    /** 角色描述 */
    private String description;

    /** 状态（0=禁用，1=正常） */
    private Integer status;
}
