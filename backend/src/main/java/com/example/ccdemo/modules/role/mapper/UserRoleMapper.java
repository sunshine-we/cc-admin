package com.example.ccdemo.modules.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ccdemo.modules.role.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联 Mapper
 *
 * @author ccDemo
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
