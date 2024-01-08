package com.hellohealth.entity.po;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("health_flash")
public class Flash {
    @TableId("FLASH_ID")
    private int flashId;
    @TableField("FLASH_TITLE")
    private String flashTitle;
    @TableField("FLASH_TIME")
    private Date flashTime;
    @TableField("FLASH_IMAGE")
    private String flashImage;
    @TableField("ADMIN_ID")
    private int adminId;
    @TableField("FLASH_CONTENT")
    private String flashContent;
}
