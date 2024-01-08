package com.hh.register.service.impl;

import com.hh.register.entity.param.AdminRegisterParam;
import com.hh.register.entity.po.AdministratorInfo;
import com.hh.register.jpa.AdministratorRepository;
import com.hh.register.jpa.UserRepository;
import com.hh.register.service.AdminRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRegisterServiceImpl implements AdminRegisterService {
    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdminRegisterServiceImpl(AdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }

    @Override
    public void insertAdminInfo(AdminRegisterParam adminRegisterParam){
        AdministratorInfo administratorInfo = new AdministratorInfo();
        administratorInfo.setAdministratorName(adminRegisterParam.getUsername());
        administratorInfo.setPhoneNumber(adminRegisterParam.getUser_phone());
        administratorInfo.setPassword(adminRegisterParam.getPassword());
        administratorInfo.setPortrait("https://s2.loli.net/2023/08/19/qPYznCTEoUy9m7c.png");
        administratorRepository.save(administratorInfo);
    }

    @Override
    public boolean hasAdminInfoByPhoneNumber(AdminRegisterParam adminRegisterParam) {
        AdministratorInfo result =
                administratorRepository.findByPhoneNumber(adminRegisterParam.getUser_phone());
        return result != null;
    }
}
