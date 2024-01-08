package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;


public interface ReportService {
    String getReports(String type,String admin_id) throws JsonProcessingException;

    String getTheComment(String report_id,String admin_id) throws JsonProcessingException;

    String reportResult(Map<String, Object> frontEndData)throws JsonProcessingException;
}
