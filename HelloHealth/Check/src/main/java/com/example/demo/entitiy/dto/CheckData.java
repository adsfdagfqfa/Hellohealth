package com.example.demo.entitiy.dto;

import com.example.demo.entitiy.po.FloorCheck;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CheckData {
    private Integer comment_id;
    private String comment_time;
    private Integer administrator_id;
    private String review_time;
    private Integer review_status;
    private String author_portrait;
    private String author_name;
    private Integer author_id;
    private String review_reason;

    public void AutoSet(FloorCheck check)
    {
        this.comment_id = check.getComment().getCommentId();
        this.comment_time = check.getComment().getCommentTime() != null ? check.getComment().getCommentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
        this.administrator_id = check.getAdministratorId();
        this.review_time = check.getReviewTime() != null ? check.getReviewTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
        this.review_status = check.getReviewStatus();
        this.author_portrait = check.getComment().getAuthor().getPortait();
        this.author_name = check.getComment().getAuthor().getUserName();
        this.author_id = check.getComment().getAuthor().getUserId();
        this.review_reason = check.getReviewReason();
    }
}
