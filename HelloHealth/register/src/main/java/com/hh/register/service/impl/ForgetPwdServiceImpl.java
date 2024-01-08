package com.hh.register.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hh.register.entity.ResultData;
import com.hh.register.entity.param.ForgetPwdParam;
import com.hh.register.entity.po.AdministratorInfo;
import com.hh.register.entity.po.UserInfo;
import com.hh.register.jpa.AdministratorRepository;
import com.hh.register.jpa.UserRepository;
import com.hh.register.service.ForgetPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ForgetPwdServiceImpl implements ForgetPwdService {
    private final ResultData resultData = new ResultData();

    private final AdministratorRepository administratorRepository;

    private final UserRepository userRepository;

    @Autowired
    public ForgetPwdServiceImpl(AdministratorRepository administratorRepository,
                                UserRepository userRepository){
        this.administratorRepository = administratorRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String UserForgetPwd(ForgetPwdParam forgetPwdParam) {
        try{
            userRepository.changeUserPwd(forgetPwdParam.getUser_phone(),forgetPwdParam.getNew_password());
            return resultData.ReturnJson();
        }catch (Exception e){
            return resultData.ReturnJson(500);
        }
    }

    @Override
    @Transactional
    public String AdminForgetPwd(ForgetPwdParam forgetPwdParam) {
        try{
            administratorRepository.changeAdminPwd(forgetPwdParam.getUser_phone(),forgetPwdParam.getNew_password());
            return resultData.ReturnJson();
        }catch (Exception e){
            return resultData.ReturnJson(500);
        }
    }

    @Override
    public boolean hasUserInfoByPhoneNumber(ForgetPwdParam forgetPwdParam) {
        UserInfo result = userRepository.findByPhoneNumber(forgetPwdParam.getUser_phone());
        return result != null;
    }

    @Override
    public boolean hasAdminInfoByPhoneNumber(ForgetPwdParam forgetPwdParam) {
        AdministratorInfo result =
                administratorRepository.findByPhoneNumber(forgetPwdParam.getUser_phone());
        return result != null;
    }
}
