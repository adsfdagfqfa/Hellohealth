package com.example.micro.service;

import javax.persistence.criteria.CriteriaBuilder;

public interface NotificationService {
    String GetNotices(Integer userId);
    String ClearNotices(Integer userId);
}
