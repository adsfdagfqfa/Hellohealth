package com.hh.userinfo.service;

import com.hh.userinfo.entity.po.Notification;

public interface NotificationService {
    boolean hasUnReadNotice(Integer userId,Integer readStatus);
}
