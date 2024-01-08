package com.hh.userinfo.repository;

import com.hh.userinfo.entity.po.FollowUser;
import com.hh.userinfo.entity.po.HBRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface HBRecordRepository extends JpaRepository<HBRecord,Integer> {
    List<HBRecord> findByUserId(Integer userId);
    
}
