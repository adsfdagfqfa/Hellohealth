package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entitiy.po.AuthenticationCheck;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthenticationCheckMapper extends BaseMapper<AuthenticationCheck> {
}
