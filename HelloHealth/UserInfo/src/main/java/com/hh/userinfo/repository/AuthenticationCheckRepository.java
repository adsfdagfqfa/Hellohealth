package com.hh.userinfo.repository;

import com.hh.userinfo.entity.po.AuthenticationCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationCheckRepository extends JpaRepository<AuthenticationCheck,Integer> {
}
