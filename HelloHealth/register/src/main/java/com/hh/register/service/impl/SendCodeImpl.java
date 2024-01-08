package com.hh.register.service.impl;

import com.hh.register.entity.ResultData;
import com.hh.register.entity.param.SendVerificationParam;
import com.hh.register.service.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Random;

@Service
public class SendCodeImpl implements SendCodeService {
    @Autowired
    public SendSmsImpl sendSms;

    @Autowired
    public RedisTemplate<String,Object> redisTemplate;

    public Random random = new Random();
    @Override
    public String sendCode(String phone) {
        ResultData resultData = new ResultData();
        //1、连接Redis，查找手机验证码是否存在
        String code = (String) redisTemplate.opsForValue().get(phone);
        SendVerificationParam ret = new SendVerificationParam();
        //====================================================
        // 1.1如果存在的话，说明在5分钟内已经发送过验证码了，不能再发了
        if (!StringUtils.isEmpty(code)) {
            ret.setUser_phone(phone);
            ret.setVerification_code(code);
            System.out.println("已存在，还没有过期，不能再次发送");
            resultData.message = "已存在，还没有过期";
            return resultData.ReturnJson();
        }
        //=====================================================

        //1.2 如果不存在的话,那么redis创建键值对生成验证码并存储，设置过期时间
        String newCode = "";
        // 生成6位随机验证码
        for (int i = 0; i < 6; i++) {
            newCode += random.nextInt(10);
        }
        // 将6位随机验证码对手机号进行发送
        boolean idSend = sendSms.send(phone,"1869816",newCode);
        System.out.println("验证码为："+newCode);
        //=====================================================

        // 因为有短信轰炸的情况，短信服务对每次发送限制次数，所以有发送不成功的情况，要考虑

        if(idSend){
            //如果发送成功将验证码存储到redis中
            redisTemplate.opsForValue().set(phone,newCode,
                    Duration.ofSeconds(300));
            System.out.println("发送成功!");
            ret.setUser_phone(phone);
            ret.setVerification_code(newCode);
            resultData.message = "发送成功";
            return resultData.ReturnJson();
        }else{
            System.out.println("发送失败!");
            resultData.message = "发送失败!";
            return resultData.ReturnJson(500);
        }
    }
}
