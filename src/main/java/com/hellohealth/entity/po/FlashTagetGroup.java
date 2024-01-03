package com.hellohealth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("flash_taget_group")
public class FlashTagetGroup {
    @TableId("GROUP_ID")
    private int groupId;
    @TableField("GROUP_NAME")
    private String groupName;
}
