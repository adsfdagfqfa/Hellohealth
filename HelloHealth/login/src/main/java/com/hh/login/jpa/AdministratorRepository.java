package com.hh.login.jpa;

import com.hh.login.entity.po.AdministratorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorInfo,Integer> {
    AdministratorInfo findByPhoneNumber(String phoneNumber);
}
