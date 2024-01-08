package com.hh.login.entity.param;

import lombok.Data;

@Data
public class LoginParam {
    private String phoneNumber;
    private String password; //必须用驼峰命名法
}
