package com.hl.hhuserinfo.controllers;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.AdminEmail;
import com.hl.hhuserinfo.service.AdminService;
import com.hl.hhuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminInfoController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/modifyInfo")
    public ResultData modifyAdminInfo(@RequestBody AdminEmail email){
        return adminService.modifyAdminInfo(1,email.getEmail());
    }

    @GetMapping("/getDetails")
    public ResultData getAdminDetails(){
        // TODO
        return adminService.getAdminInfoDetails(1);
    }
}
