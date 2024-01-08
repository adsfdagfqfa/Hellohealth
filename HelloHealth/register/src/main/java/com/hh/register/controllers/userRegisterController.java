package com.hh.register.controllers;

import com.alibaba.fastjson.JSONObject;
import com.hh.register.entity.ResultData;
import com.hh.register.entity.param.UserRegisterParam;
import com.hh.register.service.SendCodeService;
import com.hh.register.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
public class userRegisterController {
    @Autowired
    private SendCodeService sendCodeService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserRegisterService userRegisterService;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String userRegister(@RequestBody UserRegisterParam userRegisterParam){
        ResultData resultData1 = new ResultData();
        resultData1.data.put("status",false);
        if(userRegisterService.hasUserInfoByUserName(userRegisterParam))
            return resultData1.ReturnJson(102);
        JSONObject sendResult = (JSONObject) JSONObject.parse(sendCodeService.sendCode(userRegisterParam.getUser_phone()));
        if(sendResult.get("message").equals("发送成功")){
            // 前端第一次调用，那么参数当中的验证码为空，就要调用发送验证码的api，返回发送成功即可
            return sendResult.toString();
        }
        else if(sendResult.get("message").equals("已存在，还没有过期")){
            // 前端第二次调用，传入验证码，保存到数据库等操作
            // 先检测验证码是否正确
            String code = (String) redisTemplate.opsForValue().get(userRegisterParam.getUser_phone());
            if(code.equals(userRegisterParam.getVerification_code())){
                // 保存数据库等操作
                userRegisterService.insertInfo(userRegisterParam);

                return resultData1.ReturnJson();
            }
            else {
                return resultData1.ReturnJson(101);
            }
        }
        return resultData1.ReturnJson(500);
    }
}
