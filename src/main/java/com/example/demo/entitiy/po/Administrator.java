package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("administrator")
public class Administrator {
    @TableId(value = "administrator_id")
    private int administratorId;

    @TableField(value = "email")
    private String email;

    @TableField(value = "phone_number")
    private String phoneNumber;

    @TableField(value = "administrator_name")
    private String administratorName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "portrait")
    private String portrait;
}
