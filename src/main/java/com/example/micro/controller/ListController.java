package com.example.micro.controller;

import com.example.micro.model.Event;
import com.example.micro.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/toDoList")
public class ListController {

    @Autowired
    ToDoListService toDoListService;
    @GetMapping("/getEvents")
    public String getEvents(){
        return toDoListService.getEvents();
    }
    @PostMapping("/editEvents")
    public String editEvents(Event front_end_data, HttpSession session){
        return toDoListService.editEvent(front_end_data,session);
    }
    @PostMapping("/removeEvents")
    public String removeEvent(Event front_end_data, HttpSession session){
        return toDoListService.removeEvent(front_end_data,session);
    }
}