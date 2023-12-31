package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.dto.DetailFanFollowInfo;
import com.hl.hhuserinfo.entity.po.FollowUser;
import com.hl.hhuserinfo.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowUserRepository extends JpaRepository<FollowUser,FollowUser.FollowUserKeys> {
    @Query(value = "SELECT f.ids.followedId " +
            "FROM FollowUser f " +
            "WHERE f.ids.userId=:userId AND f.ids.followedId=:followedId")
    Integer findMyFollowUserId(@Param("userId")Integer userId,@Param("followedId")Integer followedId);

    @Query(value = "SELECT u " +
            "FROM FollowUser f " +
            "JOIN UserInfo u ON f.ids.followedId=u.userId " +
            "WHERE f.ids.userId=:userId")
    List<UserInfo> findFollowUserInfo(@Param("userId")Integer userId);

    @Query(value = "SELECT u " +
            "FROM FollowUser f " +
            "JOIN UserInfo u ON f.ids.userId=u.userId " +
            "WHERE f.ids.followedId=:userId")
    List<UserInfo> findFanUserInfo(@Param("userId")Integer userId);
}
