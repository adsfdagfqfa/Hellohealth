package com.hh.register.service.impl;

import com.hh.register.entity.param.UserRegisterParam;
import com.hh.register.entity.po.Hbrecord;
import com.hh.register.entity.po.Notification;
import com.hh.register.entity.po.UserInfo;
import com.hh.register.jpa.HbrecordRepository;
import com.hh.register.jpa.NotificationRepository;
import com.hh.register.jpa.UserRepository;
import com.hh.register.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserRepository userRepository;

    private final HbrecordRepository hbrecordRepository;

    private final NotificationRepository notificationRepository;

    @Autowired
    public UserRegisterServiceImpl(UserRepository userRepository,
                                   HbrecordRepository hbrecordRepository,
                                   NotificationRepository notificationRepository){
        this.userRepository=userRepository;
        this.hbrecordRepository = hbrecordRepository;
        this.notificationRepository = notificationRepository;
    }
    @Override
    public void insertInfo(UserRegisterParam userRegisterParam) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userRegisterParam.getUsername());
        userInfo.setPhoneNumber(userRegisterParam.getUser_phone());
        userInfo.setPassword(userRegisterParam.getPassword());
        userInfo.setPortait("https://s2.loli.net/2023/08/19/Vdz3Kehm2QsiBCp.png");
        userInfo.setHB_number(5);
        userInfo.setUserId(null);
        userRepository.save(userInfo);
        userInfo = userRepository.findByUserName(userRegisterParam.getUsername());
        Hbrecord hbrecord = new Hbrecord();
        hbrecord.setCHANGE_REASON("成功注册，获得杏仁币奖励");
        hbrecord.setCHANGE_TIME(LocalDateTime.now());
        hbrecord.setUSER_ID(userInfo.getUserId());
        hbrecord.setCHANGE_NUM(5);
        hbrecordRepository.save(hbrecord);
        Notification notification = new Notification();
        notification.setUSER_ID(userInfo.getUserId());
        notification.setTEXT("欢迎您加入HelloHealth！跳转这个页面帮您完善个人信息，进行医生认证(◍•ᴗ•◍)");
        notification.setURL("user");
        notificationRepository.save(notification);
    }

    @Override
    public boolean hasUserInfoByUserName(UserRegisterParam userRegisterParam){
        UserInfo result = userRepository.findByUserName(userRegisterParam.getUsername());
        return result != null;
    }
}
