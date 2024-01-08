package com.hh.login.controllers;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hh.login.entity.ResultData;
import com.hh.login.entity.param.LoginParam;
import com.hh.login.entity.po.UserInfo;
import com.hh.login.service.UserService;
import com.hh.login.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/vi/loginService/user")
public class userLoginController {
    private final UserService userService;
    private final ResultData resultData = new ResultData();

    @Autowired
    public userLoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String userLogin(@RequestBody LoginParam loginParam) {
        try{
            resultData.data.put("status",false);
            UserInfo user = userService.getUserByPhoneNumber(loginParam.getPhoneNumber());
            if(user != null && StpUtil.isLogin(user.getUser_id())){

                userService.incrementHbNumber(user.getPhoneNumber(),
                        user.getLastLoginTime());
                resultData.data.put("status",true);
                resultData.message = "已登录";
                return resultData.ReturnJson();
            }
            if(user != null && Objects.equals(loginParam.getPassword(), user.getPassword())){
                userService.incrementHbNumber(user.getPhoneNumber(),user.getLastLoginTime());
                StpUtil.login(user.getUser_id()); // satoken登录
                resultData.data.put("userId",user.getUser_id());
                return resultData.ReturnJson();
            }else return resultData.ReturnJson(103);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/loginInfo",method = RequestMethod.GET)
    public Object getLoginInfo(){
        return StpUtil.getLoginIdDefaultNull();
    }
}
