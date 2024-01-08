package com.hh.login.service;

import com.hh.login.entity.po.AdministratorInfo;

public interface AdministratorService {
    AdministratorInfo getAdminByPhoneNumber(String phoneNumber);
}
