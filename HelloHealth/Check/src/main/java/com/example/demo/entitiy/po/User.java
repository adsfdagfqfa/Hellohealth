package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName(value = "user_info")
public class User {
    @TableId(value = "user_id")
    private int userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "phone_number")
    private String phoneNumber;

    @TableField(value = "email")
    private String email;

    @TableField(value = "password")
    private String password;

    @TableField(value="gender")
    private String gender;

    @TableField(value = "portait")
    private String portait;

    @TableField(value = "birthday")
    private LocalDateTime birthday;

    @TableField(value = "signature")
    private String signature;

    @TableField(value = "hb_number")
    private double hbNumber;

    @TableField(value = "fan_number")
    private double fanNumber;

    @TableField(value = "follow_number")
    private double followNumber;

    @TableField(value = "is_approved")
    private String isApproved;

    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField(value = "is_locked")
    private LocalDateTime isLocked;

//    private List<AppliedDoctor> appliedDoctors;
//    private List<CommentReport> commentReports;
//    private List<Comment> comments;
//    private List<Hbrecord> hbrecords;
//    private List<Notification> notifications;
//    private List<Post> posts;
//    private List<StarMedicine> starMedicines;
//    private List<ToDoList> toDoLists;
//    private List<UserRewardComment> userRewardComments;
//    private List<User> followeds;
//    private List<Post> postsNavigation;
    @TableField(exist = false)
    private List<User> users;
}
