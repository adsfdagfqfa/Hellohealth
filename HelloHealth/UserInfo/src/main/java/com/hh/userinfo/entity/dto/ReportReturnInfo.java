package com.hh.userinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReportReturnInfo implements Serializable {
    Integer reportId;
    Integer reportStatus;
    String reportTime;
    String reportRespondTime;
    Integer floorNumber;
    Integer postId;
}
