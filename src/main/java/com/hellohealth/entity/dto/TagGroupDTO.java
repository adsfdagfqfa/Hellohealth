package com.hellohealth.entity.dto;

import com.hellohealth.entity.po.FlashTag;
import com.hellohealth.entity.po.FlashTagetGroup;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TagGroupDTO {
    private FlashTagetGroup group;
    private ArrayList<FlashTag> tags;
}
