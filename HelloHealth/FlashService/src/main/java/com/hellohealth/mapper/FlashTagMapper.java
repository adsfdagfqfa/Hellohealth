package com.hellohealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hellohealth.entity.po.FlashTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlashTagMapper extends BaseMapper<FlashTag> {
    List<FlashTag> selectByGroupId(int groupId);
}
