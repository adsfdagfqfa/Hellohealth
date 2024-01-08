package com.hh.register.jpa;

import com.hh.register.entity.po.AdministratorInfo;
import com.hh.register.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorInfo,Integer> {
    AdministratorInfo findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE AdministratorInfo e SET e.Password = :newPwd WHERE e.phoneNumber = :phoneNumber")
    void changeAdminPwd(@Param("phoneNumber") String phoneNumber,@Param("newPwd") String newPwd);
}
