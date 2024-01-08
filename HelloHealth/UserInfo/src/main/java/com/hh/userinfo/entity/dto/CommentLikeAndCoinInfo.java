package com.hh.userinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentLikeAndCoinInfo implements Serializable {
    boolean status;
    Integer num;
}
