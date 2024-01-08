package com.hh.login.entity.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "administrator")
public class AdministratorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMINISTRATOR_ID")
    private Integer AdministratorId;
    @Column(name = "EMAIL")
    private String Email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "ADMINISTRATOR_NAME")
    private String AdministratorName;
    @Column(name = "PASSWORD")
    private String Password;
    @Column(name = "PORTRAIT")
    private String Portrait;
}
