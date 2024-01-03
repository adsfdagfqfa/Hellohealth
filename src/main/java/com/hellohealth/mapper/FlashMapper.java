package com.hellohealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellohealth.entity.po.Flash;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlashMapper extends BaseMapper<Flash> { }
