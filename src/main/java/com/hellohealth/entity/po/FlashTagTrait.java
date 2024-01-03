package com.hellohealth.entity.po;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("flash_tag_trait")
public class FlashTagTrait {
    @TableField("TAG_ID")
    private int tagId;
    @TableField("FLASH_ID")
    private int flashId;
}
