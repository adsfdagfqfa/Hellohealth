package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("post")
public class Post {
    @TableId(value = "post_id")
    private int postId;

    @TableField("author_id")
    private int authorId;

    @TableField("title")
    private String title;

    @TableField("is_bounty")
    private Integer isBounty;

    @TableField("censor_status")
    private Integer censorStatus;

    @TableField("total_floor")
    private int totalFloor;

    @TableField(exist = false)
    private User author;
//    private List<Comment> comments = new ArrayList<>();
//    private PostWithBounty postWithBounty;
//    private List<PostTag> tags = new ArrayList<>();
//    private List<User> users = new ArrayList<>();
}
