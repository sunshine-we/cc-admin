package com.example.ccdemo.modules.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ccdemo.modules.menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联 Mapper
 *
 * @author ccDemo
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
