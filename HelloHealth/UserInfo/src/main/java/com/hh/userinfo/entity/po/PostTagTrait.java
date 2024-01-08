package com.hh.userinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@DynamicInsert
@Table(name = "post_tag_trait")
@Entity
public class PostTagTrait implements Serializable {
    @EmbeddedId
    PostTagTraitKeys ids;
    @Data
    public static class PostTagTraitKeys implements Serializable{
        @Column(name = "TAG_ID")
        Integer tagId;
        @Column(name = "POST_ID")
        Integer postId;
    }
}
