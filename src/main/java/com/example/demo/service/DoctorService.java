package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface DoctorService {
    String doctorSortBy(String type,String admin_id) throws JsonProcessingException;

    String doctorDetail(String apply_id,String admin_id) throws JsonProcessingException;

    String doctorSubmit(Map<String,Object> frontEndData) throws JsonProcessingException;
}
