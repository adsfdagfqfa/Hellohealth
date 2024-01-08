package com.hh.userinfo.repository;

import com.hh.userinfo.entity.po.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag,Integer> {
    @Query(value = "SELECT pt.displayName " +
            "FROM PostTag pt " +
            "JOIN PostTagTrait ptt ON pt.tagId=ptt.ids.tagId " +
            "WHERE ptt.ids.postId=:postId")
    List<String> findPostAllDisplayName(@Param("postId") Integer postId);
}
