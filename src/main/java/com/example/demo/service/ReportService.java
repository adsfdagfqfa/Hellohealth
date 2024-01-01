package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;


public interface ReportService {
    String getReports(Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException;

    String getTheComment(Map<String, Object> frontEndData, HttpSession session) throws JsonProcessingException;

    String reportResult(Map<String, Object> frontEndData, HttpSession session)throws JsonProcessingException;
}
