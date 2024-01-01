package com.example.demo.controller;

import com.example.demo.service.DoctorService;
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
@RequestMapping("/api/Check/Doctor")
public class CheckDoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/SortBy")
    String DoctorSortBy(@RequestBody Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return doctorService.doctorSortBy(frontEndData,session);
    }

    @PostMapping("/Detail")
    String DoctorDetail(@RequestBody Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return doctorService.doctorDetail(frontEndData,session);
    }

    @PostMapping("/Submit")
    String DoctorSubmit(@RequestBody Map<String,Object> frontEndData,HttpSession session) throws JsonProcessingException {
        return doctorService.doctorSubmit(frontEndData,session);
    }
}
