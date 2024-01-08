package com.hh.register.service;

import com.hh.register.entity.param.AdminRegisterParam;
import com.hh.register.entity.param.UserRegisterParam;

public interface AdminRegisterService {
    void insertAdminInfo(AdminRegisterParam adminRegisterParam);
    boolean hasAdminInfoByPhoneNumber(AdminRegisterParam adminRegisterParam);
}
