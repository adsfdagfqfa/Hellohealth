package com.hl.hhuserinfo.repository;

import com.hl.hhuserinfo.entity.po.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {

}
