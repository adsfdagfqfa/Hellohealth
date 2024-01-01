package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("authentication_check")
public class AuthenticationCheck {
    @TableId("apply_id")
    private int applyId;

    @TableField("administrator_id")
    private int administratorId;

    @TableField("submit_time")
    LocalDateTime submitTime;

    @TableField("review_time")
    LocalDateTime reviewTime;

    @TableField("review_status")
    private int reviewStatus;

    @TableField("review_reason")
    private String reviewReason;
}
