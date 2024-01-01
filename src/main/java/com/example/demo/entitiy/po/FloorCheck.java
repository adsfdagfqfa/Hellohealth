package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("floor_check")
public class FloorCheck {
    @TableId(value = "comment_id")
    private int commentId;

    @TableField("administrator_id")
    private Integer administratorId;

    @TableField("review_time")
    private LocalDateTime reviewTime;

    @TableField("review_status")
    private Integer reviewStatus;

    @TableField("review_reason")
    private String reviewReason;
//    private Administrator administrator;

    @TableField(exist = false)
    private Comment comment;
}