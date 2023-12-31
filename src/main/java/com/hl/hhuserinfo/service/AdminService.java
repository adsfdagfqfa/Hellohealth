package com.hl.hhuserinfo.service;

import com.hl.hhuserinfo.entity.ResultData;

public interface AdminService {
    ResultData modifyAdminInfo(Integer sessionId,String email);
    ResultData getAdminInfoDetails(Integer sessionId);
}
