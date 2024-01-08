package com.hh.register.service;

import com.hh.register.entity.param.UserRegisterParam;

public interface UserRegisterService {
    // 插入用户信息
    void insertInfo(UserRegisterParam userRegisterParam);
    boolean hasUserInfoByUserName(UserRegisterParam userRegisterParam);
}
