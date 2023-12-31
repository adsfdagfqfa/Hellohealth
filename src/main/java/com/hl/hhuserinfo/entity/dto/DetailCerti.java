package com.hl.hhuserinfo.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DetailCerti implements Serializable {
    LocalDateTime reviewTime;
    String title;
    String department;
    String hospitalRank;

    public DetailCerti(LocalDateTime reviewTime, String title, String department, String hospitalRank) {
        this.reviewTime = reviewTime;
        this.title = title;
        this.department = department;
        this.hospitalRank = hospitalRank;
    }
}
