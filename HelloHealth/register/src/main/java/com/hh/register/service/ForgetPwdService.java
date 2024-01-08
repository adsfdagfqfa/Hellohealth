package com.hh.register.service;

import com.alibaba.fastjson.JSONObject;
import com.hh.register.entity.param.ForgetPwdParam;

public interface ForgetPwdService {
    String UserForgetPwd(ForgetPwdParam forgetPwdParam);
    String AdminForgetPwd(ForgetPwdParam forgetPwdParam);
    boolean hasUserInfoByPhoneNumber(ForgetPwdParam forgetPwdParam);
    boolean hasAdminInfoByPhoneNumber(ForgetPwdParam forgetPwdParam);
}
