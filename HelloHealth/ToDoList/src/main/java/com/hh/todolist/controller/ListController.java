package com.hh.todolist.controller;

import com.hh.todolist.model.Event;
import com.hh.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/v1/toDoListService")
public class ListController {

    @Autowired
    ToDoListService toDoListService;
    @GetMapping("/list/events")
    public String getEvents(Integer userId){

        return toDoListService.getEvents(userId);
    }
    @PutMapping ("/list/events")
    public String editEvents(Event front_end_data){
        return toDoListService.editEvent(front_end_data);
    }
    @DeleteMapping ("/list/events")
    public String removeEvent(Event front_end_data){

        return toDoListService.removeEvent(front_end_data);
    }
}