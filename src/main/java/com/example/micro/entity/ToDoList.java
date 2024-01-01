package com.example.micro.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@Table(name = "to_do_list")
public class ToDoList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "TASK_ID")
    public Integer taskId;
    @Column(name="USER_ID")
    public Integer userId;
    @Column(name="IMPORTANCE")
    public String importance;
    @Column(name="CONTENT")
    public String content;
    @Column(name="START_TIME")
    public Long startTime;
    @Column(name="END_TIME")
    public Long endTime;
    @Column(name="REMINDER")
    public Integer reminder;
    @Column(name="INTERVALS")
    public Integer interval;

    // public virtual User User;
}