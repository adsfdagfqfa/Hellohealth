package com.hh.userinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "comment")
@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    Integer commentId;
    @Column(name = "AUTHOR_ID")
    Integer authorId;
    @Column(name = "COMMENT_TIME")
    LocalDateTime commentTime;
    @Column(name = "PARENT_COMMENT_ID")
    Integer parentCommentId;
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "CONTENT")
    String content;
    @Column(name = "CENSOR_STATUS")
    Integer censorStatus;
    @Column(name = "FLOOR_NUMBER")
    Integer floorNumber;
}
