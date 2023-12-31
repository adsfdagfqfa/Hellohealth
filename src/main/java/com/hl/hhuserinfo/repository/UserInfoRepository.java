package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

}
