package com.hh.register.jpa;

import com.hh.register.entity.po.Hbrecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HbrecordRepository extends JpaRepository<Hbrecord,Integer> {
}
