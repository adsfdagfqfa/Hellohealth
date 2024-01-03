package com.example.micro.controller;


import com.example.micro.model.Event;
import com.example.micro.service.NotificationService;
import com.example.micro.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/Notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/GetNotices")
    public String getEvents(Integer userId){

        return notificationService.GetNotices(userId);
    }
    @PostMapping("/ClearNotices")
    public String editEvents(Integer userId){

        return notificationService.ClearNotices(userId);
    }
}
