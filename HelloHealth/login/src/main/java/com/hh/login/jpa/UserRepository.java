package com.hh.login.jpa;

import com.hh.login.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE UserInfo e SET e.HbNumber = e.HbNumber + 1 WHERE e.phoneNumber = :phoneNumber")
    void incrementHbNumber(@Param("phoneNumber") String phoneNumber);
}
