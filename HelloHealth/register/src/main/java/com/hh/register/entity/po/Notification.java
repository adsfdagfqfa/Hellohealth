package com.hh.register.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification")
@DynamicInsert
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    Integer NOTICE_ID;
    @Column(name = "USER_ID")
    Integer USER_ID;
    @Column(name = "TIME")
    LocalDateTime TIME;
    @Column(name = "TEXT")
    String TEXT;
    @Column(name = "URL")
    String URL;
    @Column(name = "READ_STATUS")
    Integer READ_STATUS;
}
