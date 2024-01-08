package com.hh.userinfo.repository;

import com.hh.userinfo.entity.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query(value = "SELECT c.commentId " +
            "FROM Comment c " +
            "WHERE c.postId=:postId AND c.floorNumber=1 " +
            "ORDER BY c.commentId")
    List<Integer> findCommentIds(@Param("postId") Integer postId);
}
