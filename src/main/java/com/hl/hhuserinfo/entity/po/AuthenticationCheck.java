package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "authentication_check")
@Entity
public class AuthenticationCheck implements Serializable {
    @Id
    @Column(name = "APPLY_ID")
    Integer applyId;
    @Column(name = "ADMINISTRATOR_ID")
    Integer administratorId;
    @Column(name = "SUBMIT_TIME")
    LocalDateTime submitTime;
    @Column(name = "REVIEW_TIME")
    LocalDateTime reviewTime;
    @Column(name = "REVIEW_STATUS")
    Integer reviewStatus;
    @Column(name = "REVIEW_REASON")
    String reviewReason;
}
