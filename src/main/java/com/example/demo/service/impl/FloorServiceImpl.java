package com.example.demo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.demo.entitiy.dto.CheckData;
import com.example.demo.entitiy.dto.Message;
import com.example.demo.entitiy.po.*;
import com.example.demo.mapper.*;
import com.example.demo.service.FloorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorCheckMapper floorCheckMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private FollowUserMapper followUserMapper;

    @Override
    public String checkFloors(String type,String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try
        {//首先，验证身份
            Integer adminId = Integer.parseInt(admin_id);
            //AdminId = 42;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;

            //传入参数
            String type_ = type.toLowerCase();
            if (!type_.equals("unchecked") && !type_.equals("checked"))
            {
                message.setMessage("type类型错误");
                message.setErrorCode(400);
                return message.returnJson(message.getErrorCode());
            }

            //从数据库中找到相应对象
            //_context.DetachAll();
            List<FloorCheck> floorChecks = floorCheckMapper.selectList(null);
            for(FloorCheck floor:floorChecks)
            //_context.Entry(floor).Reference(p => p.Comment).Load();
            //加载每条审核对应的评论
                floor.setComment(commentMapper.selectById(floor.getCommentId()));
            List<FloorCheck> checks;
            if (type_.equals("unchecked")){
                //checks = _context.FloorChecks.OrderBy(a => a.Comment.CommentTime).ToList();//按提交时间，从旧到新排序
                QueryWrapper<FloorCheck> queryWrapperFloorCheck = new QueryWrapper<>();
                queryWrapperFloorCheck.orderByAsc("comment_time");
                checks = floorCheckMapper.selectList(queryWrapperFloorCheck);
            }
            else{
                //checks = _context.FloorChecks.OrderByDescending(a => a.ReviewTime).ToList();//按审核处理时间，从新到旧排序
                QueryWrapper<FloorCheck> queryWrapperFloorCheck = new QueryWrapper<>();
                queryWrapperFloorCheck.orderByDesc("review_time");
                checks = floorCheckMapper.selectList(queryWrapperFloorCheck);
            }
            List<CheckData> check_data = new ArrayList<>();
            for (FloorCheck check : checks)
            {
                CheckData data = new CheckData();
                if (type_ == "checked")//要获取已处理的
                {
                    if (check.getReviewStatus() == 2)//说明是未处理的
                        continue;
                }
                else//要获取未处理的
                {
                    if (check.getReviewStatus() != 2)//说明是已处理的
                        continue;
                }
                //加载数据
//                _context.Entry(check.Comment).Reference(p => p.Author).Load();
                check.setComment(commentMapper.selectById(check.getCommentId()));
                check.getComment().setAuthor(userMapper.selectById(check.getComment().getAuthorId()));
                //存入list
                data.AutoSet(check);
                check_data.add(data);
            }
            Map<String,Object> data = new HashMap<>();
            data.put("comment_list",check_data);
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e)
        {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson(message.getErrorCode());
    }

    @Override
    public String getTheFloor(String comment_id,String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try
        {//首先，验证身份
            Integer adminId = Integer.parseInt(admin_id);
            //AdminId = 42;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;
            //传入参数
            int comment_id_ = Integer.parseInt(comment_id);
            System.out.println("现在查看的细节是关于：" + comment_id_);

            //从数据库中找到相应对象
            //_context.DetachAll();
            //Comment comment=_context.Comments.Single(b=>b.CommentId == comment_id);
            Comment comment = commentMapper.selectById(comment_id_);
            Map<String,Object> data = new HashMap<>();
            data.put("post_id",comment.getPostId());
            data.put("floor_number",comment.getFloorNumber());
            data.put("content",comment.getContent());
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e)
        {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson(message.getErrorCode());
    }

    @Override
    public String checkResult(Map<String, Object> frontEndData) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        Map<String,Object> data = new HashMap<>();
        data.put("status",false);
        try
        {//首先，验证身份
            Integer adminId =(Integer)frontEndData.get("AdminID");
            //AdminId = 42;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;
            //传入参数
            int comment_id = (int)frontEndData.get("comment_id");
            boolean is_passed = frontEndData.get("is_passed").toString().equalsIgnoreCase("true");
            boolean is_blocked = frontEndData.get("is_blocked").toString().equalsIgnoreCase("true");
            if (is_passed)//如果通过了
                is_blocked = false;//如果通过了审核，那么一定不能将用户封禁。这里做了错误预防，以免前端传来错误的数据。
            String reason = frontEndData.get("review_reason").toString();
            System.out.println("调用submit接口");
            // Console.WriteLine("接收到的comment_id为：" + comment_id.ToString());

            //从数据库中找到相应对象
            //_context.DetachAll();
            //Comment comment = _context.Comments.Single(b => b.CommentId == comment_id);
            //FloorCheck floorCheck= _context.FloorChecks.Single(b => b.CommentId == comment_id);
            //_context.Entry(comment).Reference(p => p.Author).Load();//加载对应的楼主
            //_context.Entry(comment).Reference(p => p.Post).Load();//加载对应的Post
            Comment comment = commentMapper.selectById(comment_id);
            QueryWrapper<FloorCheck> queryWrapperFloorCheck = new QueryWrapper<>();
            queryWrapperFloorCheck.eq("comment_id",comment_id);
            FloorCheck floorCheck = floorCheckMapper.selectOne(queryWrapperFloorCheck);
            comment.setAuthor(userMapper.selectById(comment.getAuthorId()));
            comment.setPost(postMapper.selectById(comment.getPostId()));

            String result = "";
            if (is_passed)
            {
                result += "审核通过。";
            }
            else
                result += "审核不通过。";
            //修改帖子的审核状态
            comment.setCensorStatus (is_passed?1:0);
            if(comment.getFloorNumber()==1)//如果是一楼
            {
                //_context.Entry(comment).Reference(p => p.Post).Load();
                // 加载对应的帖子
                comment.getPost().setCensorStatus(comment.getCensorStatus());
            }
            if(is_blocked)
            {
                result += "封禁用户。";
            }
            else
                result += "不封禁用户。";
            result += "审核理由为：";
            result += (reason == "" ? "无" : reason);
            floorCheck.setReviewReason(result);
            floorCheck.setAdministratorId(adminId);
            floorCheck.setReviewTime(LocalDateTime.now());
            floorCheck.setReviewStatus(is_passed?1:0);
            // Console.WriteLine("comment_id为" + floorCheck.CommentId + "的处理结果为" + result + "\n准备保存");

            //发通知
            String url = "forum/" + comment.getPostId() + "?floor=" + comment.getFloorNumber().toString();
            if (is_passed)//如果审核通过
            {
                //首先，给发布评论的人发通知
                Notification notification = new Notification();
                notification.setUserId(comment.getAuthorId()) ;
                notification.setText("您发布的内容已经通过审核啦！感谢您持续为HelloHealth社区注入新活力");//后续可能会再补充一些信息
                notification.setUrl(url);//待改
                //_context.Notifications.Add(notification);
                notificationMapper.insert(notification);
                //给楼主发通知
                if (comment.getAuthorId() != comment.getPost().getAuthorId())//如果不是同一个人
                {
                    Notification notification2 = new Notification();
                    notification2.setUserId(comment.getPost().getAuthorId());
                    notification2.setText("有人回复了您的帖子！快去看看吧ฅ^•ﻌ•^ฅ");//后续可能会再补充一些信息
                    notification2.setUrl(url);//待改
                    //_context.Notifications.Add(notification2);
                    notificationMapper.insert(notification2);
                }
                //给关注了这个人的所有人发通知
                //User author=_context.Users.Single(b=>b.UserId == comment.AuthorId);
                User author = userMapper.selectById(comment.getAuthorId());
                //_context.Entry(author).Collection(p => p.Users).Load();
                // 加载该用户的所有粉丝
                QueryWrapper<FollowUser> queryWrapperFollowUser = new QueryWrapper<>();
                queryWrapperFollowUser.eq("user_id",author.getUserId());
                List<FollowUser> fans = followUserMapper.selectList(queryWrapperFollowUser);
                List<Integer> fan_ids = new ArrayList<>();
                for (FollowUser fan : fans)
                {
                    fan_ids.add(fan.getFollowedId());
                }
                List<User> users = userMapper.selectBatchIds(fan_ids);
                author.setUsers(users);
                for (User fan : author.getUsers())
                {
                    Notification notification0 = new Notification();
                    notification0.setText ("您关注的用户：" + author.getUserName() + "发布了新内容！戳开看看o(^▽^)o");//后续可能会再补充一些信息
                    notification0.setUrl(url);//待改
                    notification0.setUserId(fan.getUserId());
                    //_context.Notifications.Add(notification0);
                    //_context.SaveChanges();
                    notificationMapper.insert(notification0);
                }
            }
            else
            {
                //给发布评论的人发通知
                Notification notification = new Notification();
                notification.setUserId(comment.getAuthorId());
                notification.setText("很遗憾，您发布的帖子未能通过审核。管理员对您的回复是："+ result);//后续可能会再补充一些信息
                if (comment.getFloorNumber() != 1)
                    notification.setUrl("forum/" + comment.getPostId());//帖子网址
                else
                    notification.setUrl(null);
                //_context.Notifications.Add(notification);
                notificationMapper.insert(notification);
                //如果用户被封禁，并且原来没有处于被封禁状态，则另外发送一篇通知
                if (is_blocked && comment.getAuthor().getIsLocked() == null)
                {
                    Notification notification3 = new Notification();
                    notification3.setUserId(comment.getAuthor().getUserId());
                    notification3.setText("由于您发布了违规内容，现对您的账号做封禁处理，您无法继续在论坛里回复。封禁时间为：7天。");//后续可能会再补充一些信息
                    notification3.setUrl("user");//个人信息界面
                    //_context.Notifications.Add(notification3);
                    notificationMapper.insert(notification3);
                }
            }
            if(is_blocked)
                comment.getAuthor().setIsLocked(LocalDateTime.now().plusDays(7));
            //保存
            //_context.SaveChanges();
            commentMapper.updateById(comment);
            floorCheckMapper.updateById(floorCheck);
            data.put("status",true);
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e)
        {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson(message.getErrorCode());
    }

}
