package com.hh.login.service;

import com.hh.login.entity.po.UserInfo;

import java.time.LocalDateTime;

public interface UserService {
    UserInfo getUserByPhoneNumber(String phoneNumber);
    boolean isAnotherDay(LocalDateTime lastLoginTime);
    void incrementHbNumber(String phoneNumber, LocalDateTime lastLoginTime);
}
