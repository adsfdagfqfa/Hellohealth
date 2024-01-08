package com.example.demo.entitiy.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.entitiy.po.CommentReport;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportData {
    private Integer reportId;
    private Integer authorId;
    private String authorName;
    private String authorPortrait;
    private LocalDateTime commentTime;
    private Integer userId;
    private String userName;
    private String userPortrait;
    private LocalDateTime reportTime;
    private Integer administratorId;
    private LocalDateTime reportBackTime;

    public void autoSet(CommentReport report) {
        reportId = report.getReportId();
        authorId = report.getComment().getAuthor().getUserId();
        authorName = report.getComment().getAuthor().getUserName();
        authorPortrait = report.getComment().getAuthor().getPortait();
        commentTime = report.getComment().getCommentTime();
        userId = report.getUser().getUserId();
        userName = report.getUser().getUserName();
        userPortrait = report.getUser().getPortait();
        reportTime = report.getReportTime();
        administratorId = report.getAdministratorId();
        reportBackTime = report.getReportRespondTime();
    }
}
