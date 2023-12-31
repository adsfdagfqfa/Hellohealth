package com.hl.hhuserinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MainPostInfo implements Serializable {
    // 这个类用来记录一些post表和userinfo表连接得到的主要信息
    String author_name;
    String author_portrait;
    Integer post_id; // 用于后面和comment表/posttag表连接
    String post_title;

    public MainPostInfo(String author_name, String author_portrait, Integer post_id, String post_title) {
        this.author_name = author_name;
        this.author_portrait = author_portrait;
        this.post_id = post_id;
        this.post_title = post_title;
    }
}
