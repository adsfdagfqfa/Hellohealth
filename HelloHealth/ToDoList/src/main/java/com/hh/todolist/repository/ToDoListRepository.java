package com.hh.todolist.repository;

import com.hh.todolist.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findByUserId(Integer userId);

    ToDoList findByTaskId(Integer taskId);
    void deleteByTaskId(Integer taskId);
}
