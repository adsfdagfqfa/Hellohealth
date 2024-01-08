package com.hh.userinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailFanFollowInfo implements Serializable {
    int user_id;
    String user_name;
    String user_group;
    String avatar_url;
    boolean verified;
}
