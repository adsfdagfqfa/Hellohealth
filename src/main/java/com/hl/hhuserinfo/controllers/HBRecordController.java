package com.hl.hhuserinfo.controllers;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.CreateOrderParam;
import com.hl.hhuserinfo.service.HBRecordService;
import com.hl.hhuserinfo.service.UserInfoService;
import com.hl.hhuserinfo.service.impl.HBRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/userInfoService/HB")
public class HBRecordController {
    @Autowired
    private HBRecordService hbRecordService;
    @GetMapping("/record")
    ResultData getRecord(HttpSession session){
        return hbRecordService.getRecord(session);
    }
    @PutMapping("/check")
    ResultData createOrder(HttpServletRequest request){
        return hbRecordService.createOrder(request);
    }
    @PutMapping("/order")
    ResultData checkOrder(CreateOrderParam front_end_data, HttpServletRequest request){
        return hbRecordService.checkOrder(front_end_data,request);
    }
}
