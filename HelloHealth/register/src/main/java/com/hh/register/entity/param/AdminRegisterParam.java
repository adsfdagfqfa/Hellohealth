package com.hh.register.entity.param;

import lombok.Data;

@Data
public class AdminRegisterParam {
    private String username;
    private String user_phone;
    private String password;
    private String verification_code;
    private String invitation_code;
}
