package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`applied_doctor`")
public class AppliedDoctor {
    @TableId(value = "apply_id")
    private int applyId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "certification")
    private String certification;

    @TableField(value = "license")
    private String license;

    @TableField(value = "title")
    private String title;

    @TableField(value = "hospital_rank")
    private String hospitalRank;

    @TableField(value = "department")
    private String department;
}
