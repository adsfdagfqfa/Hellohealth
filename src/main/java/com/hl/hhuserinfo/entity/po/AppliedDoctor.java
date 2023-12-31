package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Data
@DynamicInsert
@Table(name = "applied_doctor")
@Entity
public class AppliedDoctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    Integer applyId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "CERTIFICATION")
    String certification;
    @Column(name = "LICENSE")
    String license;
    @Column(name = "TITLE")
    String title;
    @Column(name = "HOSPITAL_RANK")
    String hospitalRank;
    @Column(name = "DEPARTMENT")
    String department;
}
