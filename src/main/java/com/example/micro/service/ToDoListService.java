package com.example.micro.service;

import com.example.micro.model.Event;

import javax.servlet.http.HttpSession;

public interface ToDoListService {
    String getEvents();
    String editEvent(Event front_end_data, HttpSession session);
    String removeEvent(Event front_end_data, HttpSession session);
}
