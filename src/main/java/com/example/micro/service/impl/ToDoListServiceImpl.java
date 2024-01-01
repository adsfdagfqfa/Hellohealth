package com.example.micro.service.impl;

import com.example.micro.entity.ToDoList;
import com.example.micro.model.Event;
import com.example.micro.model.Message;
import com.example.micro.repository.ToDoListRepository;
import com.example.micro.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    private final ToDoListRepository toDoListRepository;

    @Autowired
    public ToDoListServiceImpl(ToDoListRepository toDoListRepository){

        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public String getEvents()
    {
        Message message = new Message();
        try
        {
            message.errorCode = 404;
            //Integer user_id = (Integer)session.getAttribute("UserID");
            Integer user_id = 121;
            if(user_id == null)
            {
                message.errorCode = 403;
                System.out.println("session中没有存userid！");
                return message.ReturnJson(message.errorCode);
            }
            System.out.println(user_id);
            //请求成功
            //从数据库找到该用户的所有事项
            //List<ToDoList> events = _context.ToDoLists.Where(b => b.UserId == user_id).ToList();
            List<ToDoList> events= toDoListRepository.findByUserId(user_id);
            System.out.println(events);
            List<Event> YourEvents = new ArrayList<>();
            //遍历事项筛选满足条件的事项
            /*foreach (ToDoList list in events)
            {
                Event _event = new Event();
                _event.id = list.TaskId;
                _event.start=list.StartTime;
                _event.end=list.EndTime;
                _event.title = list.Content;
                _event.priority = list.Importance;
                _event.notify=list.Reminder;
                _event.interval = list.Interval;
                YourEvents.Add(_event);//添加到列表中
            }*/
            for(ToDoList event:events){
                Event _event = new Event();
                _event.id=event.taskId;
                _event.start=event.startTime;
                _event.end=event.endTime;
                _event.title = event.content;
                _event.priority = event.importance;
                _event.notify=event.reminder;
                _event.interval = event.interval;
                YourEvents.add(_event);
            }
            message.data.put("events", YourEvents);
            message.errorCode = 200;//返回正确

        }
        catch (Exception error)
        {
            message.errorCode = 404;
            //System.out.println(error);
            error.printStackTrace();
        }
        return message.ReturnJson();
    }
    @Override
    public String editEvent( Event front_end_data, HttpSession session){
        Message message = new Message();
        try {
            message.errorCode = 404;
            //Integer user_id = (Integer)session.getAttribute("UserID");
            Integer user_id = 121;
            if (user_id == null)
            {
                message.errorCode = 404;
                System.out.println("session中没有存userid！");
                return message.ReturnJson(message.errorCode);
            }
            Integer task_id = front_end_data.getId();
            Long start_time = front_end_data.getStart();
            Long end_time =front_end_data.getEnd();
            String content = front_end_data.getTitle();
            String importance = front_end_data.getPriority();
            Integer reminder = front_end_data.getNotify();
            Integer interval = front_end_data.getInterval();
            if (task_id == null)
            {
                message.message = "没有传入参数";
                message.errorCode = 404;
                return message.ReturnJson(message.errorCode);
            }
            else if(task_id == -1)
            {
                //表示要新建一个事件
                ToDoList list = new ToDoList();
                list.importance = importance;
                list.content = content;
                list.startTime = start_time;
                list.endTime = end_time;
                list.reminder = reminder;
                list.userId = user_id;
                list.interval= interval;
                toDoListRepository.save(list);
                message.errorCode = 200;
                //message.data["new_id"] = list.TaskId;
                message.data.put("new_id", list.taskId);
            }
            else{
                //从数据库中找到相应对象
                ToDoList toDoList=toDoListRepository.findByTaskId(task_id);

                toDoList.startTime = start_time;
                toDoList.endTime = end_time;
                toDoList.content = content;
                toDoList.importance = importance;
                toDoList.reminder = reminder;
                toDoList.interval = interval;
                toDoListRepository.save(toDoList);//保存修改
                message.errorCode = 200;
            }
        }
        catch(Exception error){

            message.errorCode = 404;
            error.printStackTrace();
        }
        return message.ReturnJson();
    }
    @Transactional
    @Override
    public String removeEvent(Event front_end_data, HttpSession session)
    {
        Message message = new Message();
        try {
            message.errorCode = 404;
            System.out.println("删除提醒事项");
            //Integer user_id = (Integer) session.getAttribute("UserID");
            Integer user_id = 121;
            if (user_id == null)
            {
                message.errorCode = 404;
                System.out.println("session中没有存userid！");
                return message.ReturnJson(message.errorCode);
            }
            Integer task_id = front_end_data.getId();
            //_context.DetachAll();
            // Boolean isTask = _context.ToDoLists.Any(b => b.TaskId == task_id);
            ToDoList task=toDoListRepository.findByTaskId(task_id);

            if (task!=null)
            {
                /*_context.ToDoLists.Remove(_context.ToDoLists.Single(b => b.TaskId == task_id));
                _context.SaveChanges();*/

                toDoListRepository.deleteByTaskId(task_id);

                message.errorCode = 200;
                System.out.println("删除成功！");
            }
            else
            {
                System.out.println("不存在此事项！");
                message.errorCode = 404;
                return message.ReturnJson(message.errorCode);
            }
        }
        catch(Exception error){
            message.errorCode=404;
            error.printStackTrace();
        }
        return message.ReturnJson();
    }
}
