package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Data
@DynamicInsert
@Table(name = "post_tag")
@Entity
public class PostTag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    Integer tagId;
    @Column(name = "DISPLAY_NAME")
    String displayName;
}
