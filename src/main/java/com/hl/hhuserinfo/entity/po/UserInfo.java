package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "user_info")
@Entity
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "USER_NAME")
    String userName;
    @Column(name = "PHONE_NUMBER")
    String phoneNumber;
    @Column(name = "EMAIL")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "GENDER")
    String gender;
    @Column(name = "PORTAIT")
    String portait;
    @Column(name = "BIRTHDAY")
    LocalDate birthDay;
    @Column(name = "SIGNATURE")
    String signature;
    @Column(name = "HB_NUMBER")
    Integer HB_number;
    @Column(name = "FAN_NUMBER")
    Integer fanNumber;
    @Column(name = "FOLLOW_NUMBER")
    Integer followNumber;
    @Column(name = "IS_APPROVED")
    String isApproved;
    @Column(name = "LAST_LOGIN_TIME")
    LocalDateTime lastLoginTime;
    @Column(name = "IS_LOCKED")
    LocalDateTime isLocked;
}
