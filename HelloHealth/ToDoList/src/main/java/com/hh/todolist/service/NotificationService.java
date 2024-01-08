package com.hh.todolist.service;

import javax.persistence.criteria.CriteriaBuilder;

public interface NotificationService {
    String GetNotices(Integer userId);
    String ClearNotices(Integer userId);
}
