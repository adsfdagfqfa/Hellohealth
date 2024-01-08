package com.hh.login.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer user_id;
    @Column(name = "USER_NAME")
    private String UserName;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String Email;
    @Column(name = "password")
    private String Password;
    @Column(name = "GENDER")
    private String Gender;
    @Column(name = "PORTAIT")
    private String Portait;
    @Column(name = "BIRTHDAY")
    private LocalDate Birthday;
    @Column(name = "SIGNATURE")
    private String Signature;
    @Column(name = "HB_NUMBER")
    private BigDecimal HbNumber;
    @Column(name = "FAN_NUMBER")
    private BigDecimal FanNumber;
    @Column(name = "FOLLOW_NUMBER")
    private BigDecimal FollowNumber;
    @Column(name = "IS_APPROVED")
    private String IsApproved;
    @Column(name = "LAST_LOGIN_TIME")
    private LocalDateTime LastLoginTime;
    @Column(name = "IS_LOCKED")
    private LocalDateTime IsLocked;
}
