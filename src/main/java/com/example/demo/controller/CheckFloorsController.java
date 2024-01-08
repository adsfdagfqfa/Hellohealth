package com.example.demo.controller;

import com.example.demo.service.FloorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/CheckService/check/floor")
public class CheckFloorsController {
    @Autowired
    private FloorService floorService;

    @GetMapping("/sortBy")
    String CheckFloors(@RequestParam String type,@RequestParam String admin_id) throws JsonProcessingException {
        return floorService.checkFloors(type,admin_id);
    }

    @GetMapping("/detail")
    String GetTheFloor(@RequestParam String comment_id , @RequestParam String admin_id) throws JsonProcessingException {
        return floorService.getTheFloor(comment_id,admin_id);
    }

    @PostMapping("/submit")
    String CheckResult(@RequestBody Map<String, Object> frontEndData) throws JsonProcessingException {
        return floorService.checkResult(frontEndData);
    }
}
