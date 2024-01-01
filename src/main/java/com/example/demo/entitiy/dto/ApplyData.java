package com.example.demo.entitiy.dto;

import lombok.Data;

@Data
public class ApplyData {
    private Integer applyId;
    private String submitTime;
    private Integer userId;
    private String userName;
    private String userPortrait;
    private Integer reviewStatus;
    private String reviewTime;
    private Integer administratorId;
    private String reviewReason;
}
