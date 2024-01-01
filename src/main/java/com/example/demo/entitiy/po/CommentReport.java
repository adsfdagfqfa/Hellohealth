package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.AdministratorMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment_report")
public class CommentReport {

    @TableId(value = "report_id",type = IdType.ASSIGN_ID)
    private int reportId;

    @TableField("comment_id")
    private Integer commentId;

    @TableField("user_id")
    private Integer userId;

    @TableField("administrator_id")
    private Integer administratorId;

    @TableField("report_time")
    private LocalDateTime reportTime;

    @TableField("report_reason")
    private String reportReason;

    @TableField("report_status")
    private Integer reportStatus;

    @TableField("report_respond")
    private String reportRespond;

    @TableField("report_respond_time")
    private LocalDateTime reportRespondTime;



//    @TableField(exist = false)
//    private Administrator administrator;

    @TableField(exist = false)
    private Comment comment;

    @TableField(exist = false)
    private User user;

//    private AdministratorMapper administratorMapper;
//    private CommentMapper commentMapper;
//    private UserMapper userMapper;
//    public void mySetAdministrator() {
//        QueryWrapper<Administrator> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("administrator_id", this.administratorId);
//        this.administrator = administratorMapper.selectOne(queryWrapper);
//    }
//
//    public void mySetComment(){
//        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("comment_id",this.commentId);
//        this.comment = commentMapper.selectOne(queryWrapper);
//    }
//
//    public void mySetUser(){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id",this.userId);
//        this.user = userMapper.selectOne(queryWrapper);
//    }
//
//    public void setNotExist(){
//        mySetAdministrator();
//        mySetUser();
//        mySetComment();
//    }
}
