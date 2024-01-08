package com.hh.todolist.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;


import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@DynamicInsert
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="NOTICE_ID")
    public Integer noticeId;
    @Column(name="USER_ID")
    public Integer userId;
    @Column(name="TIME")
    public LocalDateTime time;
    @Column(name="TEXT")
    public String text ;
    @Column(name="URL")
    public String url ;
    @Column(name="READ_STATUS")
    public Integer read;
}
