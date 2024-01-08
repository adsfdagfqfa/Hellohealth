package com.hh.register.entity.param;

import lombok.Data;

@Data
public class UserRegisterParam {
    private String username;
    private String user_phone;
    private String password;
    private String verification_code;
}
