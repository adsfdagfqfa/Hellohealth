package com.hh.userinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReportInfo implements Serializable {
    Integer reportId;
    Integer reportStatus;
    LocalDateTime reportTime;
    LocalDateTime reportRespondTime;
    Integer floorNumber;
    Integer postId;

    public ReportInfo(Integer reportId, Integer reportStatus, LocalDateTime reportTime, LocalDateTime reportRespondTime, Integer floorNumber, Integer postId) {
        this.reportId = reportId;
        this.reportStatus = reportStatus;
        this.reportTime = reportTime;
        this.reportRespondTime = reportRespondTime;
        this.floorNumber = floorNumber;
        this.postId = postId;
    }
}
