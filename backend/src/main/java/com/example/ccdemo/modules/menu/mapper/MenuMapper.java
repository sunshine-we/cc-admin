package com.example.ccdemo.modules.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ccdemo.modules.menu.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 Mapper
 *
 * @author ccDemo
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
