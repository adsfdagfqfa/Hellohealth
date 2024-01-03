package com.example.micro.controller;

import com.example.micro.model.Event;
import com.example.micro.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/ToDoList")
public class ListController {

    @Autowired
    ToDoListService toDoListService;
    @GetMapping("/GetEvents")
    public String getEvents(Integer userId){

        return toDoListService.getEvents(userId);
    }
    @PostMapping("/EditEvents")
    public String editEvents(Event front_end_data){
        return toDoListService.editEvent(front_end_data);
    }
    @PostMapping("/RemoveEvents")
    public String removeEvent(Event front_end_data){
        return toDoListService.removeEvent(front_end_data);
    }
}