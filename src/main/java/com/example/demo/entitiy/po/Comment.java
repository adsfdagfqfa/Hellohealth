package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("comment")
public class Comment {
    @TableId(value = "comment_id",type = IdType.ASSIGN_ID)
    private int commentId;

    @TableField("author_id")
    private int authorId;

    @TableField("comment_time")
    private LocalDateTime commentTime;

    @TableField("parent_comment_id")
    private Integer parentCommentId;

    @TableField("post_id")
    private int postId;

    @TableField("content")
    private String content;

    @TableField("censor_status")
    private Integer censorStatus;

    @TableField("floor_number")
    private Integer floorNumber;


    @TableField(exist = false)
    private User author;

    @TableField(exist = false)
    private List<CommentReport> commentReports;

    @TableField(exist = false)
    private FloorCheck floorCheck;

    @TableField(exist = false)
    private Post post;

    @TableField(exist = false)
    private List<PostWithBounty> postWithBounties;
//    @TableField(exist = false)
//    private List<UserRewardComment> userRewardComments;
}
