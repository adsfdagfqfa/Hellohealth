package com.hellohealth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("flash_tag")
public class FlashTag {
    @TableId("TAG_ID")
    private int tagId;
    @TableField("TAG_NAME")
    private String tagName;
    @TableField("GROUP_ID")
    private int groupId;
}
