package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "hb_record")
@Entity
public class HBRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    Integer RECORD_ID;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name="CHANGE_NUM")
    Integer CHANGE_NUM;
    @Column(name="CHANGE_TIME")
    LocalDateTime CHANGE_TIME;
    @Column(name="CHANGE_REASON")
    String CHANGE_REASON;

}
