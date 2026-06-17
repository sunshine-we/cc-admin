package com.example.ccdemo.modules.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ccdemo.modules.role.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色 Mapper
 *
 * @author ccDemo
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
