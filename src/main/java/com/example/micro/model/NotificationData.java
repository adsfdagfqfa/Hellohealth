package com.example.micro.model;

import com.example.micro.entity.Notification;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NotificationData {
    public String message;
    public Boolean unread;//如果read==0，则unread=1
    public String time;
    public String url;
    public void AutoSet(Notification notification)
    {
        message = notification.text;
        unread = (notification.read==0);
       // time = ).ToString("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        time=(notification.time).format(formatter);
        url = notification.url;
    }

}
