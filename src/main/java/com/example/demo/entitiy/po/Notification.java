package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "notification")
public class Notification {
    @TableId(value = "notice_id")
    private int noticeId;

    @TableField(value = "user_id")
    private int userId;

    @TableField(value = "time")
    private LocalDateTime time;

    @TableField(value = "text")
    private String text;

    @TableField(value = "url")
    private String url;

    @TableField(value = "read")
    private int read;
//    private User user;
}
