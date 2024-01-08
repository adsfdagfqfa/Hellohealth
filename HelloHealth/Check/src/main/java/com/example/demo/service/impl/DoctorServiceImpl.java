package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entitiy.dto.ApplyData;
import com.example.demo.entitiy.dto.Message;
import com.example.demo.entitiy.po.AppliedDoctor;
import com.example.demo.entitiy.po.AuthenticationCheck;
import com.example.demo.entitiy.po.Notification;
import com.example.demo.entitiy.po.User;
import com.example.demo.mapper.AppliedDoctorMapper;
import com.example.demo.mapper.AuthenticationCheckMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AuthenticationCheckMapper authenticationCheckMapper;

    @Autowired
    private AppliedDoctorMapper appliedDoctorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public String doctorSortBy(String type,String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try
        {//首先，验证身份
            Integer adminId = Integer.parseInt(admin_id);
            // AdminId = 43;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;
            //传入参数
            String type_ = type;
            if (!type_.equals("unchecked") && !type_.equals("checked"))
            {
                message.setMessage("type类型错误");
                message.setErrorCode(400);
                return message.returnJson(message.getErrorCode());
            }

            //从数据库中找到相应对象
            //_context.DetachAll();
            List<AuthenticationCheck> checks;
            if (type_.equals("unchecked")){
                //checks = _context.AuthenticationChecks.OrderBy(a => a.SubmitTime).ToList();//按提交时间，从旧到新排序
                QueryWrapper<AuthenticationCheck> queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByAsc("submit_time");
                checks = authenticationCheckMapper.selectList(queryWrapper);
            }
            else{
                //checks = _context.AuthenticationChecks.OrderByDescending(a => a.ReviewTime).ToList();//按审核处理时间，从晚到早排序
                QueryWrapper<AuthenticationCheck> queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByDesc("review_time");
                checks = authenticationCheckMapper.selectList(queryWrapper);
            }
            List<ApplyData> apply_data = new ArrayList<>();
            for (AuthenticationCheck check : checks)
            {
                ApplyData data = new ApplyData();
                if (type_.equals("checked"))//要获取已审核的
                {
                    if (check.getReviewStatus() == 2)//说明是未审核的则不需要
                        continue;
                }
                else//要获取未审核的
                {
                    if (check.getReviewStatus() != 2)//说明是已审核的跳过
                        continue;
                }
                data.setApplyId(check.getApplyId());
                LocalDateTime submitTime = check.getSubmitTime();
                int year = submitTime.getYear();
                int month = submitTime.getMonth().getValue();
                int day = submitTime.getDayOfMonth();
                int hour = submitTime.getHour();
                int minute = submitTime.getMinute();
                int second = submitTime.getSecond();

                String formattedTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
                data.setSubmitTime(formattedTime);
                //需要知道申请者的userid
//                var result = from a in _context.AppliedDoctors
//                join b in _context.AuthenticationChecks on a.ApplyId equals b.ApplyId
//                where b.ApplyId == check.ApplyId
//                select new { a.UserId };
                QueryWrapper<AppliedDoctor> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apply_id",check.getApplyId());
                List<AppliedDoctor> result = new ArrayList<>();
                result = appliedDoctorMapper.selectList(queryWrapper);

                for (AppliedDoctor item : result)
                {
                    data.setUserId(item.getUserId());
                    //User user = _context.Users.Single(b => b.UserId == data.user_id);
                    User user  = new User();
                    user = userMapper.selectById(item.getUserId());
                    data.setUserName(user.getUserName());
                    data.setUserPortrait(user.getPortait());
                    break;//其实也没有必要
                }
                //接下来要获取审核状态
                data.setReviewStatus(check.getReviewStatus());
                //接下来获取审核时间
                if(check.getReviewTime()!=null)
                {
                    LocalDateTime reviewTime = (LocalDateTime) check.getReviewTime();
                    int year0 = reviewTime.getYear();
                    int month0 = reviewTime.getMonthValue();
                    int day0 = reviewTime.getDayOfMonth();
                    int hour0 = reviewTime.getHour();
                    int minute0 = reviewTime.getMinute();
                    int second0 = reviewTime.getSecond();

                    String formattedTime0 = String.format("%04d-%02d-%02d %02d:%02d:%02d", year0, month0, day0, hour0, minute0, second0);
                    data.setReviewTime(formattedTime0);
                }
                else
                {
                    data.setReviewTime(null);
                }
                data.setAdministratorId(check.getAdministratorId());
                data.setReviewReason(check.getReviewReason());
                apply_data.add(data);
            }
            Map<String,Object> data = message.getData();
            data.put("apply_list",apply_data);
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e)
        {
            message.setErrorCode(500);
            System.out.println(e.toString());
            message.setMessage(e.getMessage());
        }
        return message.returnJson();
    }

    @Override
    public String doctorDetail(String apply_id,String admin_id) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode(500);
        try
        {//首先，验证身份
            Integer adminId = Integer.parseInt(admin_id);
            //AdminId = 43;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;
            //传入参数
            Integer apply_id_ = Integer.parseInt(apply_id);
            if (apply_id_==null)
            {
                message.setMessage("没有传入参数");
                message.setErrorCode(400);
                return message.returnJson(message.getErrorCode());
            }

            //从数据库中找到相应对象
            //_context.DetachAll();
            //AppliedDoctor appliedDoctor = _context.AppliedDoctors.Single(a => a.ApplyId ==apply_id);
            AppliedDoctor appliedDoctor = appliedDoctorMapper.selectById(apply_id_);
            Map<String,Object> data = message.getData();
            data.put("certification",appliedDoctor.getCertification());
            data.put("license",appliedDoctor.getLicense());
            data.put("title",appliedDoctor.getTitle());
            data.put("hospital_rank",appliedDoctor.getHospitalRank());
            data.put("department",appliedDoctor.getDepartment());
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
    public String doctorSubmit(Map<String, Object> frontEndData) throws JsonProcessingException {
        Message message = new Message();
        message.setErrorCode (500);
        try
        {//首先，验证身份
            Map<String,Object> data = new HashMap<>();
            data.put("status",false);
            Integer adminId = (Integer) frontEndData.get("admin_id");
            //AdminId = 43;
            if (adminId == null)
            {
                message.setErrorCode(403);
                System.out.println("session中没有存Admin ID！");
                data.put("status",false);
                return message.returnJson(message.getErrorCode());
            }
//            Integer adminId = 42;
            //传入、验证参数
            Integer apply_id = (Integer) frontEndData.get("apply_id");
            boolean is_passed = (boolean) frontEndData.get("is_passed");
            String reason = frontEndData.get("reason").toString();
            String doctor_title = frontEndData.get("doctor_title").toString();
            String doctor_department = frontEndData.get("doctor_department").toString();
            //string doctor_hospital = front_end_data.GetProperty("doctor_hospital").GetString();
            String doctor_hospital_rank = frontEndData.get("doctor_hospital_rank").toString();
            System.out.println(doctor_title);
            if (apply_id == null)
            {
                message.setMessage("没有传入参数");
                message.setErrorCode(400);
                data.put("status",false);
                return message.returnJson(message.getErrorCode());
            }
            //从数据库中找到相应对象
            //_context.DetachAll();
            QueryWrapper<AppliedDoctor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_id",apply_id);
            appliedDoctorMapper.selectCount(queryWrapper);
            boolean isAppliedDoctor = appliedDoctorMapper.selectCount(queryWrapper)>0;
            if (!isAppliedDoctor)
            {
                message.setMessage("该申请ID不存在！");
                message.setErrorCode(404);
                data.put("status",false);
                return message.returnJson(message.getErrorCode());
            }
            //AppliedDoctor appliedDoctor= _context.AppliedDoctors.Single(b => b.ApplyId == apply_id);//根据申请id找到对应的医生
            AppliedDoctor appliedDoctor = appliedDoctorMapper.selectById(apply_id);
            //获取此用户的Userid
            int user_id = (int)appliedDoctor.getUserId();
            //下面填写AuthenticationCheck新值
            //AuthenticationCheck authenticationCheck = _context.AuthenticationChecks.Single(b => b.ApplyId == apply_id);
            AuthenticationCheck authenticationCheck = authenticationCheckMapper.selectById(apply_id);
            authenticationCheck.setAdministratorId (adminId);//填写负责管理员
            authenticationCheck.setReviewTime(LocalDateTime.now());//填写认证审核时间
            if(is_passed)//表示通过
            {
                System.out.println("审核通过！");
                //下面填写AppliedDoctor新值
                appliedDoctor.setTitle(doctor_title);
                appliedDoctor.setDepartment(doctor_department);
                //appliedDoctor.Hospital = doctor_hospital;
                appliedDoctor.setHospitalRank(doctor_hospital_rank);
                //填写完毕
                authenticationCheck.setReviewStatus(1);
                authenticationCheck.setReviewReason ("审核通过。" + reason);
                //同时更新User的isApproved
                //User user = _context.Users.Single(b => b.UserId == user_id);
                User user = userMapper.selectById(user_id);
                user.setIsApproved("y");
                //审核通过所以发送通知
                Notification notification = new Notification();
                notification.setUserId(user.getUserId());
                notification.setText("恭喜您已通过医生认证！HelloHealth感谢您加入我们，点击按钮跳转到社区，为大家提供专业性的帮助和建议。");
                notification.setUrl("forum");//转到论坛界面
                //_context.Notifications.Add(notification);
                notificationMapper.insert(notification);
                //_context.SaveChanges();//保存
                userMapper.updateById(user);
                appliedDoctorMapper.updateById(appliedDoctor);
                authenticationCheckMapper.updateById(authenticationCheck);
            }
            else//表示不通过
            {
                System.out.println("审核不通过！");
                authenticationCheck.setReviewStatus(0);
                authenticationCheck.setReviewReason("审核不通过。" + reason);
                //_context.SaveChanges();//将更改保存到数据库
                authenticationCheckMapper.updateById(authenticationCheck);
                //同时更新User的isApproved
                //User user = _context.Users.Single(b => b.UserId == user_id);
                User user = userMapper.selectById(user_id);
                user.setIsApproved("n");
                //_context.SaveChanges();//将更改保存到数据库
                userMapper.updateById(user);
                //发送通知
                Notification notification = new Notification();
                notification.setUserId(user.getUserId());
                notification.setText("很遗憾，您的医生认证并未通过审核，请前往个人信息界面，上传正确的证件照片。");
                notification.setUrl("user");//转到个人信息界面
                //_context.Notifications.Add(notification);
                notificationMapper.insert(notification);
                //_context.SaveChanges();//保存
                userMapper.updateById(user);
                authenticationCheckMapper.updateById(authenticationCheck);
            }
            data.put("status",true);
            message.setData(data);
            message.setErrorCode(200);
        }
        catch (Exception e)
        {

            message.setErrorCode(500);
            System.out.println(e.toString());
            Map<String,Object> data = new HashMap<>();
            data.put("status",false);
            message.setData(data);
            message.setMessage(e.getMessage());
        }
        return message.returnJson(message.getErrorCode());
    }

}
