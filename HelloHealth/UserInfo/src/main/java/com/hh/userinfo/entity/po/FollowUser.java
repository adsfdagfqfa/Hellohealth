package com.hh.userinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

@Data
@DynamicInsert
@Table(name = "follow_user")
@Entity
public class FollowUser implements Serializable {
    @EmbeddedId
    FollowUserKeys ids;
    @Data
    public static class FollowUserKeys implements Serializable{
        @Column(name = "USER_ID")
        Integer userId;
        @Column(name = "FOLLOWED_ID")
        Integer followedId;
    }
    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserInfo fan;
}
