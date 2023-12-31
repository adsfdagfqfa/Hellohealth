package com.hl.hhuserinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifiedAdminInfo implements Serializable {
    //管理员ID
    int id;
    //管理员姓名
    String name;
    //管理员电话
    String telephone;
    //管理员email
    String email;
    //管理员头像
    String portrait;
}
