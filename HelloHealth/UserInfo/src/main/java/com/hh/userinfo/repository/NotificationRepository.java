package com.hh.userinfo.repository;

import com.hh.userinfo.entity.po.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    Optional<Notification> findByUserIdAndReadStatus(Integer userId,Integer readStatus);
}
