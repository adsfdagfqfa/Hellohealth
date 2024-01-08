package com.hh.userinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Data
@DynamicInsert
@Table(name = "administrator")
@Entity
public class Administrator implements Serializable {
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
