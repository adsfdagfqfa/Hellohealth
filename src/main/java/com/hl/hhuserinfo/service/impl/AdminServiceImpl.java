package com.hl.hhuserinfo.service.impl;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.ModifiedAdminInfo;
import com.hl.hhuserinfo.entity.po.Administrator;
import com.hl.hhuserinfo.repository.AdministratorRepository;
import com.hl.hhuserinfo.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdministratorRepository administratorRepository;

    public AdminServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public ResultData modifyAdminInfo(Integer sessionId, String email) {
        try{
            Optional<Administrator> adminOrNULL = administratorRepository.findById(sessionId);
            if(adminOrNULL.isEmpty())
                return ResultData.fail("管理员未登录");
            Administrator admin = adminOrNULL.get();
            admin.setEmail(email);
            administratorRepository.save(admin);
            Map<String, Object> result = getStringObjectMap(admin);
            return ResultData.ok(result);
        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("查找数据库失败");
        }
    }

    private static Map<String, Object> getStringObjectMap(Administrator admin) {
        ModifiedAdminInfo modifiedAdminInfo = new ModifiedAdminInfo();
        modifiedAdminInfo.setEmail(admin.getEmail());
        modifiedAdminInfo.setId(admin.getAdministratorId());
        modifiedAdminInfo.setName(admin.getAdministratorName());
        modifiedAdminInfo.setTelephone(admin.getPhoneNumber());
        modifiedAdminInfo.setPortrait(admin.getPortrait());
        Map<String,Object> result = new HashMap<>();
        result.put("administrator",modifiedAdminInfo);
        result.put("isAdministrator",true);
        result.put("isLogin",true);
        return result;
    }

    @Override
    public ResultData getAdminInfoDetails(Integer sessionId) {
        try{
            Optional<Administrator> adminOrNULL = administratorRepository.findById(sessionId);
            if(adminOrNULL.isEmpty())
                return ResultData.fail("管理员未登录");
            Administrator admin = adminOrNULL.get();
            administratorRepository.save(admin);
            Map<String, Object> result = getStringObjectMap(admin);
            return ResultData.ok(result);
        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("查找数据库失败");
        }
    }
}
