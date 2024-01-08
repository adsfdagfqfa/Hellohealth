package com.example.micro.controller;


import com.example.micro.model.Event;
import com.example.micro.service.NotificationService;
import com.example.micro.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/v1/toDoListService")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/notification/notices")
    public String getNotices(Integer userId){

        return notificationService.GetNotices(userId);
    }
    @DeleteMapping ("/notification/notices")
    public String clearNotices(Integer userId){

        return notificationService.ClearNotices(userId);
    }
}
