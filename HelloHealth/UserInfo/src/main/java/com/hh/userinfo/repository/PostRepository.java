package com.hh.userinfo.repository;

import com.hh.userinfo.entity.dto.MainPostInfo;
import com.hh.userinfo.entity.po.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT NEW com.hh.userinfo.entity.dto.MainPostInfo(u.userName,u.portait,p.postId,p.title) " +
            "FROM Post p " +
            "JOIN UserInfo u ON p.authorId=u.userId " +
            "WHERE p.authorId=:authorId")
    List<MainPostInfo> findMainPostInfo(@Param("authorId") Integer authorId);
}
