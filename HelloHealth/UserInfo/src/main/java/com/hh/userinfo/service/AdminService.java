package com.hh.userinfo.service;

import com.hh.userinfo.entity.ResultData;

public interface AdminService {
    ResultData modifyAdminInfo(Integer sessionId,String email);
    ResultData getAdminInfoDetails(Integer sessionId);
}
