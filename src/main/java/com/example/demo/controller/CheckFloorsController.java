package com.example.demo.controller;

import com.example.demo.service.FloorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/Check/Floor")
public class CheckFloorsController {
    @Autowired
    private FloorService floorService;

    @PostMapping("/SortBy")
    String CheckFloors(@RequestBody Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return floorService.checkFloors(frontEndData,session);
    }

    @PostMapping("/Detail")
    String GetTheFloor(@RequestBody Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return floorService.getTheFloor(frontEndData,session);
    }

    @PostMapping("/Submit")
    String CheckResult(@RequestBody Map<String, Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return floorService.checkResult(frontEndData,session);
    }
}
