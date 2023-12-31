package com.hl.hhuserinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifiedUserInfo implements Serializable {
    String gender;
    String email;
    String birthday;
    String signature;
}
