package com.hh.login.controllers;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.login.entity.ResultData;
import com.hh.login.entity.param.LoginParam;
import com.hh.login.entity.po.AdministratorInfo;
import com.hh.login.service.AdministratorService;
import com.hh.login.service.impl.AdministratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/loginService/admin")
public class adminLoginController {
    private final AdministratorService administratorService;
    private final ResultData resultData = new ResultData();

    @Autowired
    public adminLoginController(AdministratorService administratorService){
        this.administratorService = administratorService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.PUT)
    public String adminLogin(@RequestBody LoginParam loginParam) {
        try{
            resultData.data.put("status",false);
            AdministratorInfo admin = administratorService.getAdminByPhoneNumber(loginParam.getPhoneNumber());
            if(admin != null && StpUtil.isLogin(admin.getAdministratorId())){
                resultData.data.put("status",true);
                resultData.message = "已登录";
                return resultData.ReturnJson();
            }
            if(admin != null && Objects.equals(loginParam.getPassword(), admin.getPassword())){
                StpUtil.login(admin.getAdministratorId()); // satoken登录
                resultData.data.put("status",true);
                resultData.data.put("adminId",admin.getAdministratorId());
                return resultData.ReturnJson();
            }else return resultData.ReturnJson(103);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/loginInfo",method = RequestMethod.GET)
    public Long getLoginInfo(){
        return StpUtil.getLoginIdAsLong();
    }
}
