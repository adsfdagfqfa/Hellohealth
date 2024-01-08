package com.hh.register.controllers;

import com.alibaba.fastjson.JSONObject;
import com.hh.register.entity.ResultData;
import com.hh.register.entity.param.ForgetPwdParam;
import com.hh.register.service.ForgetPwdService;
import com.hh.register.service.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class forgetPwdController {
    @Autowired
    private SendCodeService sendCodeService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ForgetPwdService forgetPwdService;

    @RequestMapping(value = "/api/v1/user/pwd",method = RequestMethod.PUT)
    public String userForgetPwd(@RequestBody ForgetPwdParam forgetPwdParam){
        ResultData resultData1 = new ResultData();
        if(!forgetPwdService.hasUserInfoByPhoneNumber(forgetPwdParam))
            return resultData1.ReturnJson(106);
        JSONObject sendResult = (JSONObject) JSONObject.parse(sendCodeService.sendCode(forgetPwdParam.getUser_phone()));
        if(sendResult.get("message").equals("发送成功")){
            // 前端第一次调用，那么参数当中的验证码为空，就要调用发送验证码的api，返回发送成功即可
            return sendResult.toString();
        }
        else if(sendResult.get("message").equals("已存在，还没有过期")){
            // 前端第二次调用，传入验证码，保存到数据库等操作
            // 先检测验证码是否正确
            String code = (String) redisTemplate.opsForValue().get(forgetPwdParam.getUser_phone());
            if(code.equals(forgetPwdParam.getVerification_code())){
                forgetPwdService.UserForgetPwd(forgetPwdParam);
                return resultData1.ReturnJson();
            }
            else {
                return resultData1.ReturnJson(400);
            }
        }
        return resultData1.ReturnJson(500);
    }

    @RequestMapping("/api/v1/admin/pwd")
    public String adminForgetPwd(@RequestBody ForgetPwdParam forgetPwdParam){
        ResultData resultData1 = new ResultData();
        if(!forgetPwdService.hasAdminInfoByPhoneNumber(forgetPwdParam))
            return resultData1.ReturnJson(106);
        JSONObject sendResult = (JSONObject) JSONObject.parse(sendCodeService.sendCode(forgetPwdParam.getUser_phone()));
        if(sendResult.get("message").equals("发送成功")){
            // 前端第一次调用，那么参数当中的验证码为空，就要调用发送验证码的api，返回发送成功即可
            return sendResult.toString();
        }
        else if(sendResult.get("message").equals("已存在，还没有过期")){
            // 前端第二次调用，传入验证码，保存到数据库等操作
            // 先检测验证码是否正确
            String code = (String) redisTemplate.opsForValue().get(forgetPwdParam.getUser_phone());
            if(code.equals(forgetPwdParam.getVerification_code())){
                forgetPwdService.AdminForgetPwd(forgetPwdParam);
                return resultData1.ReturnJson();
            }
            else {
                return resultData1.ReturnJson(400);
            }
        }
        return resultData1.ReturnJson(500);
    }
}
