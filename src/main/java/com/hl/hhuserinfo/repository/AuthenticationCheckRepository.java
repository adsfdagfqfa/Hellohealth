package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.po.AuthenticationCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationCheckRepository extends JpaRepository<AuthenticationCheck,Integer> {
}
