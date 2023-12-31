package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "post")
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "AUTHOR_ID")
    Integer authorId;
    @Column(name = "TITLE")
    String title;
    @Column(name = "IS_BOUNTY")
    Integer isBounty ;
    @Column(name = "CENSOR_STATUS")
    Integer censorStatus;
    @Column(name = "TOTAL_FLOOR")
    Integer totalFloor;
}
