package com.example.micro.service;

import com.example.micro.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;

public interface ToDoListService {
    String getEvents(Integer userId);
    String editEvent(Event front_end_data);
    String removeEvent(Event front_end_data);
}
