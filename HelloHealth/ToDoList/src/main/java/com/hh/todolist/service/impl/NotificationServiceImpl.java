package com.hh.todolist.service.impl;

import com.hh.todolist.entity.Notification;
import com.hh.todolist.model.Message;
import com.hh.todolist.model.NotificationData;
import com.hh.todolist.repository.NotificationRepository;
import com.hh.todolist.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    @Autowired
    NotificationServiceImpl( NotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
    }
    @Override
    public String GetNotices(Integer userId) {
        Message message = new Message();
        try
        {
            //message.data["status"] = false;
            message.data.put("status",false);
            //Console.WriteLine($"{HttpContext.Request.Scheme}://{HttpContext.Request.Host}{HttpContext.Request.Path}{HttpContext.Request.QueryString}");

            //验证身份
            //Integer user_id = session.GetInt32("UserID");
            Integer user_id = userId;
            if (user_id == null)
            {
                message.data.put("msg", "用户未登录");
                message.errorCode = 403;
                return message.ReturnJson(message.errorCode);
            }
            //从数据库找到相应对象
            List<Notification> temp=notificationRepository.findByUserID(user_id);//按时间从新到旧
            //创建链表
            List<NotificationData> notices = new ArrayList<>();
            for (Notification notification:temp)
            {
                NotificationData data= new NotificationData();
                data.AutoSet(notification);
                notices.add(data);
                //将消息置为已读
                notification.read = 1;
                notificationRepository.save(notification);//保存修改
            }
            //保存数据库中的更改
            //_context.SaveChanges();
            //返回给前端
            message.data.put("notifications",notices);
            message.data.put("status", true);
            message.errorCode = 200;
        }
        catch (NoSuchElementException e)
        {
            message.data.put("msg", e.getMessage());
            System.out.println(e.getMessage());
            message.errorCode = 400;
        }
        catch (IllegalStateException e)
        {
            message.data.put("msg",e.getMessage());
            System.out.println(e.getMessage());
            message.errorCode = 404;
        }
        catch (Exception e)
        {
            message.data.put("msg", e.getMessage());
            message.errorCode = 500;
            System.out.println(e.getMessage());
        }
        return message.ReturnJson(message.errorCode);
    }
    @Transactional
    @Override
    public String ClearNotices(Integer userId) {
        Message message = new Message();
        try
        {
            message.data.put( "status", false);

            //验证身份
            //Integer user_id = session.GetInt32("UserID");
            Integer  user_id = userId;
            if (user_id == null)
            {
                message.data.put( "msg", "用户未登录");
                message.errorCode = 403;
                return message.ReturnJson(message.errorCode);
            }
            //在数据库中删除该用户的已读通知
            //_context.Notifications.RemoveRange(_context.Notifications.Where(b => b.UserId == user_id&&b.Read==1));
            //保存数据库中的更改
            //_context.SaveChanges();
            notificationRepository.deleteByUserIdAndRead(user_id,1);//删除指定用户的已经读取的消息
            //返回给前端
            message.data.put( "status", true);
            message.errorCode = 200;
        }
        catch (NoSuchElementException e)
        {
            message.data.put( "msg", e.getMessage());
            System.out.println(e.getMessage());
            message.errorCode = 404;
        }
        catch (Exception e)
        {
            message.data.put( "msg", e.getMessage());
            message.errorCode = 500;
            System.out.println(e.getMessage());
        }
        return message.ReturnJson(message.errorCode);

    }
}
