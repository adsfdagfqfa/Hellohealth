package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface FloorService {
    String checkFloors(Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException;

    String getTheFloor(Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException;

    String checkResult(Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException;
}
