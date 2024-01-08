package com.hh.register.jpa;


import com.hh.register.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

    UserInfo findByUserName(String userName);

    UserInfo findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE UserInfo e SET e.password = :newPwd WHERE e.phoneNumber = :phoneNumber")
    void changeUserPwd(@Param("phoneNumber") String phoneNumber, @Param("newPwd") String newPwd);
}
