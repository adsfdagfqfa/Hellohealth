package com.hh.userinfo.service.impl;

import com.hh.userinfo.entity.po.Notification;
import com.hh.userinfo.repository.NotificationRepository;
import com.hh.userinfo.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public boolean hasUnReadNotice(Integer userId, Integer readStatus) {
        Optional<Notification> unreadNotice = notificationRepository.findByUserIdAndReadStatus(userId,readStatus);
        return unreadNotice.isPresent();
    }
}
