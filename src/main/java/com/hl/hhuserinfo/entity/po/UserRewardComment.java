package com.hl.hhuserinfo.entity.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@DynamicInsert
@Table(name = "user_reward_comment")
@Entity
public class UserRewardComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REWARD_ID")
    Integer rewardId;
    @Column(name = "COMMENT_ID")
    Integer commentId;
    @Column(name = "REWARD_TIME")
    LocalDateTime rewardTime;
    @Column(name = "REWARD_TYPE")
    String rewardType;
    @Column(name = "REWARD_VALUE")
    Integer rewardValue;
    @Column(name = "GIVER_USER_ID")
    Integer giverUserId;
}
