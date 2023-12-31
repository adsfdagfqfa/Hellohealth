package com.hl.hhuserinfo.service;

import com.hl.hhuserinfo.entity.po.Notification;

public interface NotificationService {
    boolean hasUnReadNotice(Integer userId,Integer readStatus);
}
