package com.example.demo.controller;


import com.example.demo.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.example.demo.service.impl.ReportServiceImpl;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/Check/Report")
public class CheckCommentsController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/SortBy")
    public String getReports(@RequestBody Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return reportService.getReports(frontEndData,session);
    }

    @PostMapping("/Detail")
    String getTheComment(@RequestBody Map<String, Object> frontEndData, HttpSession session) throws JsonProcessingException {
        return reportService.getTheComment(frontEndData,session);
    }

    @PostMapping("/Submit")
    String reportResult(@RequestBody Map<String, Object> frontEndData, HttpSession session) throws JsonProcessingException{
        return reportService.reportResult(frontEndData,session);
    }

}
