package com.hh.userinfo.repository;

import com.hh.userinfo.entity.dto.ReportInfo;
import com.hh.userinfo.entity.po.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport,Integer> {
    @Query(value = "SELECT NEW com.hh.userinfo.entity.dto.ReportInfo(cr.reportId,cr.reportStatus,cr.reportTime,cr.reportRespondTime,c.floorNumber,c.postId) " +
            "FROM CommentReport cr " +
            "JOIN Comment c ON c.commentId=cr.commentId " +
            "WHERE cr.userId=:userId")
    List<ReportInfo> findReportInfo(@Param("userId") Integer userId);
}
