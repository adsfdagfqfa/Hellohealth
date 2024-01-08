package com.hh.userinfo.controllers;

import com.hh.userinfo.entity.ResultData;
import com.hh.userinfo.entity.dto.AdminEmail;
import com.hh.userinfo.service.AdminService;
import com.hh.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userInfoService/admin")
public class AdminInfoController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/info")
    public ResultData modifyAdminInfo(@RequestBody AdminEmail email){
        return adminService.modifyAdminInfo(1,email.getEmail());
    }

    @GetMapping("/details")
    public ResultData getAdminDetails(){
        return adminService.getAdminInfoDetails(1);
    }
}
