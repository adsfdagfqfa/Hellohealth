package com.hh.login.service.impl;

import com.hh.login.entity.po.UserInfo;
import com.hh.login.jpa.UserRepository;
import com.hh.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    // 这里可以再扩展其他的服务
    @Override
    public UserInfo getUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean isAnotherDay(LocalDateTime lastLoginTime){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return lastLoginTime.toLocalDate().isEqual(currentDateTime.toLocalDate());
    }
    @Transactional
    @Override
    public void incrementHbNumber(String phoneNumber, LocalDateTime lastLoginTime){
        // 这个函数不仅要完成杏仁币自增，还要修改登录时间
        UserInfo tmpUser = getUserByPhoneNumber(phoneNumber);
        tmpUser.setLastLoginTime(LocalDateTime.now());
        userRepository.save(tmpUser);
        if(!isAnotherDay(lastLoginTime))
            userRepository.incrementHbNumber(phoneNumber);
    }


}
