package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface FloorService {
    String checkFloors(String type,String admin_id) throws JsonProcessingException;

    String getTheFloor(String comment_id,String admin_id) throws JsonProcessingException;

    String checkResult(Map<String, Object> frontEndData) throws JsonProcessingException;
}
