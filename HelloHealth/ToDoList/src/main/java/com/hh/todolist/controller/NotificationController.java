package com.hh.todolist.controller;


import com.hh.todolist.model.Event;
import com.hh.todolist.service.NotificationService;
import com.hh.todolist.service.ToDoListService;
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
