package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName(value = "hb_record")
public class Hbrecord {
    @TableId(value = "record_id")
    private int recordId;

    @TableField(value = "user_id")
    private int userId;

    @TableField(value = "change_num")
    private int changeNum;

    @TableField(value = "change_time")
    private LocalDateTime changeTime;

    @TableField(value = "change_reason")
    private String changeReason;
//    private User user;
}
