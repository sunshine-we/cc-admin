package com.example.ccdemo.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ccdemo.modules.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 *
 * @author ccDemo
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
