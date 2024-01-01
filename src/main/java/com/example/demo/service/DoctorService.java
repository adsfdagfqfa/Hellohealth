package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface DoctorService {
    String doctorSortBy(Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException;

    String doctorDetail(Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException;

    String doctorSubmit(Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException;
}
