package com.hh.userinfo.repository;

import com.hh.userinfo.entity.dto.DetailCerti;
import com.hh.userinfo.entity.po.AppliedDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppliedDoctorRepository extends JpaRepository<AppliedDoctor,Integer> {
    // 获取认证的那个日期和职称和所在医院
    // 需要自然连接审核表和申请表进行查询，user_id=xxx and review_status=1 and 根据根据申请ID进行降序排列选择
    // 最大的那一个也就意味着最后申请的那一个
    // 获得最新的且通过审核的apply
    @Modifying
    @Query(value = "SELECT NEW com.hh.userinfo.entity.dto.DetailCerti(b.reviewTime,a.title,a.department,a.hospitalRank) " +
            "FROM AppliedDoctor a " +
            "JOIN AuthenticationCheck b ON a.applyId=b.applyId " +
            "WHERE a.userId=:userId AND b.reviewStatus=1 " +
            "ORDER BY a.applyId DESC ")
    List<DetailCerti> findAuthCertificationInfo(@Param("userId")Integer userId);

    AppliedDoctor findByCertificationAndLicense(String cert,String license);
}
