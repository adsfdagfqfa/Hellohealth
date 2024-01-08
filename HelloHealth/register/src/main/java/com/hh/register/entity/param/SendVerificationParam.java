package com.hh.register.entity.param;

import lombok.Data;

@Data
public class SendVerificationParam {
    private String user_phone;
    private String verification_code;
}
