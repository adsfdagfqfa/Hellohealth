package com.hl.hhuserinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailUserInfo implements Serializable {
    //用户ID
    public int userID;
    //是否进行认证
    public boolean isCertified;
    //用户的杏仁币
    public int numOfCoin;
    //用户的姓名
    public String userName;
    //用户的性别
    public String gender;
    //用户的生日
    public String birthday;
    //用户的手机号
    public String telephone;
    //用户的邮箱
    public String email;
    //用户的个人描述
    public String description;
    //用户的头像
    public String avatarUrl;
}
