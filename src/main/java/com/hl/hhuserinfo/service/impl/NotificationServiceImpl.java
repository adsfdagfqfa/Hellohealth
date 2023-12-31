package com.hl.hhuserinfo.service.impl;

import com.hl.hhuserinfo.entity.po.Notification;
import com.hl.hhuserinfo.repository.NotificationRepository;
import com.hl.hhuserinfo.service.NotificationService;
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
