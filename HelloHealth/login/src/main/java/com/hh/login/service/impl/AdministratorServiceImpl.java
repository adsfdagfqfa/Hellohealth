package com.hh.login.service.impl;

import com.hh.login.entity.po.AdministratorInfo;
import com.hh.login.jpa.AdministratorRepository;
import com.hh.login.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }

    @Override
    public AdministratorInfo getAdminByPhoneNumber(String phoneNumber){
        return administratorRepository.findByPhoneNumber(phoneNumber);
    }
}
