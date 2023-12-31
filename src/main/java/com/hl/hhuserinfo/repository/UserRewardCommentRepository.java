package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.po.UserRewardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardCommentRepository extends JpaRepository<UserRewardComment,Integer> {
    @Query(value = "SELECT COUNT(u) " +
            "FROM UserRewardComment u " +
            "WHERE u.commentId=:commentId AND u.rewardType='like' ")
    Integer findLikeNum(@Param("commentId") Integer commentId);
    @Query(value = "SELECT COUNT(u.rewardValue) " +
            "FROM UserRewardComment u " +
            "WHERE u.commentId=:commentId AND u.rewardType='coin' ")
    Integer findCoinNum(@Param("commentId") Integer commentId);
}
