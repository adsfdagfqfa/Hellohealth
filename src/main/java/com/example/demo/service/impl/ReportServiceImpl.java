package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entitiy.dto.ReportData;
import com.example.demo.entitiy.dto.Message;
import com.example.demo.entitiy.po.*;
import com.example.demo.mapper.*;
import com.example.demo.service.ReportService;
import com.example.demo.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CommentReportMapper commentReportMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostWithBountyMapper postWithBountyMapper;

    @Autowired
    private HbrecordMapper hbrecordMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private FloorCheckMapper floorCheckMapper;

    @Autowired
    private Utils utils;


    @Override
    public String getReports(String type,String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try{
            // 验证身份
            Integer adminId = (Integer) admin_id;
            if (adminId == null) {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson();
            }
//            Integer adminId=41;

            // 传入参数
            String type = type.toLowerCase();
            if (!type.equals("unchecked") && !type.equals("checked")) {
                message.setMessage("type类型错误");
                message.setErrorCode(400);
                return message.returnJson();
            }

            // 从数据库中找到相应对象
            List<CommentReport> reports;
            if (type.equals("unchecked")) {
                QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByDesc("report_time");
                reports = commentReportMapper.selectList(queryWrapper);
//                for(CommentReport report : reports){
//                    report.setNotExist();
//                }
            } else {
                QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByAsc("report_respond_time");
                reports = commentReportMapper.selectList(queryWrapper);
//                for(CommentReport report:reports){
//                    report.setNotExist();
//                }
            }

            List<ReportData> reportDataList = new ArrayList<>();
            for (CommentReport report : reports) {
                if (type.equals("checked") && report.getReportStatus() == 2) {
                    continue;
                } else if (type.equals("unchecked") && report.getReportStatus() != 2) {
                    continue;
                }
                // 加载数据
                report.setComment(commentMapper.selectById(report.getCommentId()));
                report.setUser(userMapper.selectById(report.getUserId()));
                report.getComment().setAuthor(userMapper.selectById(report.getComment().getAuthorId()));
                // 存入list
                ReportData data = new ReportData();
                data.autoSet(report);
                reportDataList.add(data);
            }
            message.getData().put("report_list", reportDataList);
            message.setErrorCode(200);
        }
        catch (Exception e) {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson();
    }

    @Override
    public String getTheComment(String report_id, String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try {
            // 验证身份
            Integer adminId = (Integer) admin_id;
            if (adminId == null) {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson();
            }
//            Integer adminId=41;

            // 传入参数
            Integer reportId = (Integer)report_id;
            System.out.println("现在查看的细节是关于："+ reportId.toString());

            // 从数据库中找到相应对象
            CommentReport report;
            QueryWrapper<CommentReport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("report_id",reportId);
            report = commentReportMapper.selectOne(queryWrapper);
            // 加载数据
            report.setComment(commentMapper.selectById(report.getCommentId()));
            report.setUser(userMapper.selectById(report.getUserId()));
            report.getComment().setPost(postMapper.selectById(report.getComment().getPostId()));


            Map<String,Object> data = new HashMap<>();
            data.put("post_id",report.getComment().getPostId());
            data.put("post_title",report.getComment().getPost().getTitle());
            data.put("floor_number",report.getComment().getFloorNumber());
            data.put("report_reason",report.getReportReason());
//            message.data["post_id"] = report.Comment.PostId;
//            message.data["post_title"] = report.Comment.Post.Title;
//            message.data["floor_number"] = report.Comment.FloorNumber;
//            message.data["report_reason"] = report.ReportReason;
            if (report.getComment().getParentCommentId() != null)
                //message.data["content"] = GetJson(report.Comment.Content);
                //说明是一个回复，它原本是string类型，需要返回为json格式
                data.put("content",utils.getJson(report.getComment().getContent()));
            else
                //message.data["content"] = report.Comment.Content;
                //楼层 直接返回
                data.put("content",report.getComment().getContent());
            if (report.getReportStatus() != 2)//说明已处理
                //message.data["report_respond"] = report.ReportRespond;
                data.put("report_respond",report.getReportRespond());
            else
                //message.data["report_respond"] = "";
                data.put("report_respond","");
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e) {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson();
    }

    @Override
    public String reportResult(Map<String,Object> frontEndData)throws JsonProcessingException{
        Message message = new Message();
        message.setErrorCode(500);
        Map<String,Object> data = new HashMap<>();
        data.put("status",false);
        try{
            // 验证身份
            Integer adminId = (Integer) frontEndData.get("AdminID");
            if (adminId == null) {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson();
            }
//            Integer adminId=41;
            int report_id = (Integer) frontEndData.get("report_id");
            String is_deletedValue = frontEndData.get("is_deleted").toString().toLowerCase();
            boolean is_deleted = is_deletedValue.equals("true");
            String is_blockedValue = frontEndData.get("is_blocked").toString().toLowerCase();
            boolean is_blocked = is_blockedValue.equals("true");
            if(is_deleted == false)
                is_blocked=false;//如果不删除原评论，说明该评论是合乎规矩的，那么不应该封禁用户。
            String report_respond = frontEndData.get("report_respond").toString();
            System.out.println("调用submit接口");

            //从数据库中找到相应对象
            CommentReport report;
            QueryWrapper<CommentReport> queryWrapperCommentReport = new QueryWrapper<>();
            queryWrapperCommentReport.eq("report_id",report_id);
            report = commentReportMapper.selectOne(queryWrapperCommentReport);

            //加载数据
            report.setComment(commentMapper.selectById(report.getCommentId()));
            report.setUser(userMapper.selectById(report.getUserId()));

            User blocked_user;
            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("user_id",report.getComment().getAuthorId());//report对应的comment
            blocked_user =  userMapper.selectOne(queryWrapperUser);//是被举报的人

            //执行操作
            String result = "";
            QueryWrapper<Post> queryWrapperPost = new QueryWrapper<>();
            queryWrapperPost.eq("post_id",report.getComment().getPostId());
            Post post = postMapper.selectOne(queryWrapperPost);
            if(is_deleted){
                //deleteComment(report.Comment, AdminId);
                //接下来需要进行删除
                if(report.getComment().getFloorNumber() != 1) {
                    if (report.getComment().getParentCommentId() == null) {
                        //说明这是一个楼层，所有该楼下的内容都应该做处理
                        QueryWrapper<PostWithBounty> queryWrapperPostWithBounty = new QueryWrapper<>();
                        queryWrapperPostWithBounty.eq("best_answer_comment_id",report.getComment().getCommentId());
                        if(postWithBountyMapper.selectCount(queryWrapperPostWithBounty)>0)
                        {
                            System.out.println("要删除的答案是作者选定的最佳答案");
                            queryWrapperPostWithBounty.eq("post_id",post.getPostId());
                            postWithBountyMapper.delete(queryWrapperPostWithBounty);

                            post.setIsBounty(0);
                            postMapper.updateById(post);
                        }
                        QueryWrapper<Comment> queryWrapperComment = new QueryWrapper<>();
                        queryWrapperComment.eq("floor_number",report.getComment().getFloorNumber());
                        List<Comment> commentsToRemove = commentMapper.selectList(queryWrapperComment);

                        //找到该楼层下的所有评论
                        for(Comment commentToRemove : commentsToRemove)
                        {
                            //加载每个评论对应的举报
                            queryWrapperCommentReport.eq("comment_id",commentToRemove.getCommentId());
                            commentToRemove.setCommentReports(commentReportMapper.selectList(queryWrapperCommentReport));

                            for(CommentReport comment_report : commentToRemove.getCommentReports())//修改这些举报的内容
                                if (comment_report.getReportStatus() == 2) {
                                    comment_report.setReportStatus(0);
                                    comment_report.setAdministratorId(adminId);
                                    comment_report.setReportRespond("楼层被举报，楼层中的回复级联删除");
                                    comment_report.setReportRespondTime(LocalDateTime.now());
                                    System.out.println("该楼层下被修改的report是" + comment_report.getReportId());
                                    //_context.SaveChanges();
                                    commentReportMapper.updateById(comment_report);
                                }
                            //修改回复的审核状态
                            commentToRemove.setCensorStatus(0);
                            //_context.SaveChanges();
                            commentMapper.updateById(commentToRemove);
                        }
                        //修改楼层数
                        post.setTotalFloor(post.getTotalFloor()-1);
                    } else {
                        //这是一个回复。它涉及的表格包括：CommentReports，UserRewardComments

                        queryWrapperCommentReport.eq("Comment_id",report.getComment().getCommentId());
                        List<CommentReport> commentReports = commentReportMapper.selectList(queryWrapperCommentReport);
                        ;//该评论可能曾经被多次举报
                        for(CommentReport report1 : commentReports)//这些举报如果是未处理的，则置为已处理。需要修改并保存commentReports
                        {
                            if (report1.getReportStatus() == 2) {
                                report1.setReportStatus(0);
                                report1.setAdministratorId(adminId);
                                report1.setReportRespond("该回复已被其他管理员删除");
                                report1.setReportRespondTime(LocalDateTime.now());
                                System.out.println("被修改的report是" + report1.getReportId());
                                //_context.SaveChanges();
                                commentReportMapper.updateById(report1);
                            }
                        }
                        //修改回复的审核状态
                        report.getComment().setCensorStatus(0);
                        System.out.println("回复的审核状态已变为0.id是" + report.getComment().getCommentId());
                    }
                }else{
                    //这是一楼，要修改所有相应内容
                    QueryWrapper<Comment> queryWrapperComment = new QueryWrapper<>();
                    queryWrapperComment.eq("post_id",post.getPostId());
                    List<Comment> commentsToRemove = commentMapper.selectList(queryWrapperComment);//从上下文的所有评论中，找到该post的评论
                    //管理员审核表
                    for(Comment commentToRemove : commentsToRemove)
                    {
                        //加载每个评论对应的举报
                        //_context.Entry(commentToRemove).Collection(p => p.CommentReports).Load();
                        for(CommentReport comment_report : commentToRemove.getCommentReports())//修改这些举报的内容
                            if (comment_report.getReportStatus() == 2)
                            {
                                comment_report.setReportStatus(0);
                                comment_report.setAdministratorId(adminId);
                                comment_report.setReportRespond("帖子被举报，帖子中的回复级联删除");
                                comment_report.setReportRespondTime(LocalDateTime.now());
                                //_context.SaveChanges();
                                commentReportMapper.updateById(comment_report);
                                System.out.println("该帖子中被修改的report是" + comment_report.getReportId());
                            }
                        if (commentToRemove.getParentCommentId() == null)//说明这是一个楼层
                        {
                            //加载这个楼层对应的审核
                            //_context.Entry(commentToRemove).Reference(p => p.FloorCheck).Load();
                            commentToRemove.setFloorCheck(floorCheckMapper.selectById(commentToRemove.getCommentId()));
                            if (commentToRemove.getFloorCheck().getReviewStatus() == 2)//如果是未处理的
                            {
                                commentToRemove.getFloorCheck().setAdministratorId(adminId);
                                commentToRemove.getFloorCheck().setReviewTime(LocalDateTime.now());
                                commentToRemove.getFloorCheck().setReviewStatus(0);
                                commentToRemove.getFloorCheck().setReviewReason("该帖子已被其他管理员删除");
                            }
                        }
                        //修改comment的审核状态
                        commentToRemove.setCensorStatus(0);
                        System.out.println("回复的审核状态已变为0.id是" + commentToRemove.getCommentId());
                        //_context.SaveChanges();
                        commentMapper.updateById(commentToRemove);
                    }
                    //修改post的审核状态
                    post.setCensorStatus(0);
                    //加载数据
                    post.setAuthor(userMapper.selectById(post.getAuthorId()));
                    //_context.Entry(post).Reference(p => p.Author).Load();
                    //杏仁币返还
                    if (post.getIsBounty() == 1) {
                        QueryWrapper<PostWithBounty> queryWrapperPostWithBounty = new QueryWrapper<>();
                        queryWrapperPostWithBounty.eq("post_id",post.getPostId());
                        PostWithBounty PWB = postWithBountyMapper.selectOne(queryWrapperPostWithBounty);
                        if (PWB.getBestAnswerCommentId() == null)//还没有设置最佳答案
                        {
                            post.getAuthor().setHbNumber(post.getAuthor().getHbNumber()+ (double) PWB.getBountyValue() / 2);
                            if (PWB.getBountyValue() / 2 > 0) {
                                //登记杏仁币变动原因
                                Hbrecord record = new Hbrecord();
                                record.setChangeNum(PWB.getBountyValue() / 2);
                                record.setUserId(post.getAuthor().getUserId());
                                record.setChangeTime(LocalDateTime.now());
                                record.setChangeReason("您发布的悬赏帖经管理员审核，发现有不健康的内容，已被删除。系统扣留悬赏金额的一半，其余退还给您。");
                                //_context.Hbrecords.Add(record);
                                hbrecordMapper.insert(record);
                            }
                        }
                        //把该帖子从悬赏列表中删掉
                        //_context.PostWithBounties.Remove(PWB);
                        postWithBountyMapper.deleteById(PWB.getPostId());
                    }
                }

                result += "删除原评论。";
            }else{
                result += "不删除原评论。";
            }
            if(is_blocked)
                result += "封禁用户。";
            else
                result += "不封禁用户。";
            result += "处理原因：";
            result+= (report_respond == "" ?   "无":report_respond);
            report.setReportRespond(result);
            report.setAdministratorId(adminId);
            if (is_blocked || is_deleted)
                report.setReportStatus(0);//表示原评论没有通过审核
            else
                report.setReportStatus(1);
            report.setReportRespondTime(LocalDateTime.now());

            //如果举报成功，发送通知
            if (is_deleted)
            {
                String url = "forum/" + post.getPostId() ;//待改
                //先给被举报的人
                Notification notification = new Notification();
                notification.setUserId(blocked_user.getUserId());
                notification.setText("您发布的内容含有不健康的内容，已被管理员删除");//后续可能会再补充一些信息
                notification.setUrl(url);

                //_context.Notifications.Add(notification);
                notificationMapper.insert(notification);
                //发给发起举报的人
                Notification notification2 = new Notification();
                notification2.setUserId(report.getUserId());
                notification2.setText("您提交的举报已被受理，感谢您对社区秩序的维护。管理员的处理结果为：" + result);//后续可能会再补充一些信息
                notification2.setUrl(url);
                //_context.Notifications.Add(notification2);
                notificationMapper.insert(notification2);
                //如果用户被封禁，另外发送一篇通知
                if (is_blocked&&blocked_user.getIsLocked()==null)
                {
                    Notification notification3 = new Notification();
                    notification3.setUserId(blocked_user.getUserId());
                    notification3.setText("由于您发布了违规内容，现对您的账号做封禁处理，您无法继续在论坛里回复。封禁时间为：7天。");//后续可能会再补充一些信息
                    notification3.setUrl("user");//个人信息界面
                    //_context.Notifications.Add(notification3);
                    notificationMapper.insert(notification3);
                }
            }
            if(is_blocked)
                blocked_user.setIsLocked(LocalDateTime.now().plusDays(7));//在最后修改locked状态
            //_context.SaveChanges();
            userMapper.updateById(blocked_user);
            commentReportMapper.updateById(report);

            data.put("status",true);
            message.setData(data);
            message.setErrorCode(200);
        }
        catch(Exception e){
            message.setErrorCode(500);
            System.out.println(e);
            message.setMessage(e.getMessage());
        }
        return message.returnJson(message.getErrorCode());
    }

}

