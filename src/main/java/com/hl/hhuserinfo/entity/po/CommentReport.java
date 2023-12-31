package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "comment_report")
@Entity
public class CommentReport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    Integer reportId;
    @Column(name = "COMMENT_ID")
    Integer commentId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "ADMINISTRATOR_ID")
    Integer adminId;
    @Column(name = "REPORT_TIME")
    LocalDateTime reportTime;
    @Column(name = "REPORT_REASON")
    String reportReason;
    @Column(name = "REPORT_STATUS")
    Integer reportStatus;
    @Column(name = "REPORT_RESPOND")
    String reportRespond;
    @Column(name = "REPORT_RESPOND_TIME")
    LocalDateTime reportRespondTime;
}
