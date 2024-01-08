package com.hh.register.controllers;

import com.alibaba.fastjson.JSONObject;
import com.hh.register.entity.ResultData;
import com.hh.register.entity.param.AdminRegisterParam;
import com.hh.register.service.AdminRegisterService;
import com.hh.register.service.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/admin")
public class adminRegisterController {
    @Autowired
    private SendCodeService sendCodeService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private AdminRegisterService adminRegisterService;

    private final String[] invitation_codes = {"2054099","2152193"};

    private final ResultData resultData = new ResultData();

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String adminRegister(@RequestBody AdminRegisterParam adminRegisterParam){
        ResultData resultData1 = new ResultData();
        resultData1.data.put("status",false);
        if(!Arrays.asList(invitation_codes).contains(adminRegisterParam.getInvitation_code())){
            resultData1.data.put("errorType","wrong invitation code");
            return resultData1.ReturnJson();
        }
        if(adminRegisterService.hasAdminInfoByPhoneNumber(adminRegisterParam)){
            resultData1.data.put("errorType","UserName already registered");
            return resultData1.ReturnJson();
        }
        JSONObject sendResult = (JSONObject) JSONObject.parse(sendCodeService.sendCode(adminRegisterParam.getUser_phone()));
        if(sendResult.get("message").equals("发送成功")){
            // 前端第一次调用，那么参数当中的验证码为空，就要调用发送验证码的api，返回发送成功即可
            return sendResult.toString();
        }
        else if(sendResult.get("message").equals("已存在，还没有过期")){
            // 前端第二次调用，传入验证码，保存到数据库等操作
            // 先检测验证码是否正确
            String code = (String) redisTemplate.opsForValue().get(adminRegisterParam.getUser_phone());
            if(code.equals(adminRegisterParam.getVerification_code())){
                // 保存数据库等操作
                adminRegisterService.insertAdminInfo(adminRegisterParam);
                resultData1.data.put("status",true);
                return resultData1.ReturnJson();
            }
            else {
                resultData1.data.put("errorType","wrong invitation code");
                return resultData1.ReturnJson();
            }
        }
        return resultData1.ReturnJson(500);
    }
}
