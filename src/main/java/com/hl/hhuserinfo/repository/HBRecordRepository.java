package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.po.FollowUser;
import com.hl.hhuserinfo.entity.po.HBRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface HBRecordRepository extends JpaRepository<HBRecord,Integer> {
    List<HBRecord> findByUserId(Integer userId);
    
}
