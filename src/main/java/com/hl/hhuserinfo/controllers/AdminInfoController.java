package com.hl.hhuserinfo.controllers;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.AdminEmail;
import com.hl.hhuserinfo.service.AdminService;
import com.hl.hhuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminInfoController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/info")
    public ResultData modifyAdminInfo(@RequestBody AdminEmail email){
        return adminService.modifyAdminInfo(1,email.getEmail());
    }

    @GetMapping("/Details")
    public ResultData getAdminDetails(){
        // TODO
        return adminService.getAdminInfoDetails(1);
    }
}
