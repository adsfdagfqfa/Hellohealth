package com.example.micro.repository;

import com.example.micro.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query(value ="SELECT  n FROM Notification n WHERE (n.userId=:id) ORDER BY n.time DESC")
    List<Notification> findByUserID(@Param("id") Integer user_id);

    void deleteByUserIdAndRead(Integer userId,Integer read);
}
