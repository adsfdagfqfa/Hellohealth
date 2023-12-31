package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "notification")
@Entity
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    Integer noticeId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "TIME")
    LocalDateTime time;
    @Column(name = "TEXT")
    String text;
    @Column(name = "URL")
    String url;
    @Column(name = "READ_STATUS")
    Integer readStatus;
}
