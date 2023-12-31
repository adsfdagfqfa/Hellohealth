package com.hl.hhuserinfo.service;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.CreateOrderParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface HBRecordService {
    ResultData getRecord(HttpSession session);
    ResultData createOrder(HttpServletRequest request);
    ResultData checkOrder(CreateOrderParam front_end_data, HttpServletRequest request);
}
