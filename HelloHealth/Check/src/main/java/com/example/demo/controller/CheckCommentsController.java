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
@RequestMapping("/api/v1/CheckService/check/report")
public class CheckCommentsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/sortBy")
    public String getReports(@RequestParam String type,@RequestParam String admin_id) throws JsonProcessingException {
        return reportService.getReports(type,admin_id);
    }

    @GetMapping("/detail")
    String getTheComment(@RequestParam String report_id, @RequestParam String admin_id) throws JsonProcessingException {
        return reportService.getTheComment(report_id,admin_id);
    }

    @PostMapping("/submit")
    String reportResult(@RequestBody Map<String, Object> frontEndData) throws JsonProcessingException{
        return reportService.reportResult(frontEndData);
    }

}
