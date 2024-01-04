package com.example.micro.model;

import lombok.Data;

@Data
public class Event {

    public Integer id ;
    public Long start;
    public Long end ;
    public String title ;
    public String priority;
    public Boolean notify ;
    public Integer interval ;
    public Integer userId;
}
