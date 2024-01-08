package com.hh.register.entity.param;

import lombok.Data;

@Data
public class ForgetPwdParam {
    String user_phone;
    String new_password;
    String verification_code;
}
