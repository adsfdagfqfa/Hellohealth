package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("follow_user")
public class FollowUser {
    @TableId(value = "user_id")
    private Integer userId;

    @TableId(value = "followed_id")
    private Integer followedId;
}
