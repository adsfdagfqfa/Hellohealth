package com.example.demo.entitiy.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "post_with_bounty")
public class PostWithBounty {

    @TableId(value = "post_id")
    private int postId;

    @TableField(value = "bounty_value")
    private int bountyValue;

    @TableField(value = "best_answer_comment_id")
    private Integer bestAnswerCommentId;
//    private Comment beatAnswerComment;
//    private Post post = null;
}
