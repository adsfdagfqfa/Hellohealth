package com.example.demo.controller;

import com.example.demo.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/CheckService/check/doctor")
public class CheckDoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/sortBy")
    String DoctorSortBy(@RequestParam String type,@RequestParam String admin_id) throws JsonProcessingException {
        return doctorService.doctorSortBy(type,admin_id);
    }

    @GetMapping("/detail")
    String DoctorDetail(@RequestParam String apply_id,@RequestParam String admin_id) throws JsonProcessingException {
        return doctorService.doctorDetail(apply_id,admin_id);
    }

    @PostMapping("/submit")
    String DoctorSubmit(@RequestBody Map<String,Object> frontEndData) throws JsonProcessingException {
        return doctorService.doctorSubmit(frontEndData);
    }
}
