package com.hellohealth.entity.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ModifyFlashRequest {
    private String content;
    private Integer flash_being_edited_id;
    private ArrayList<String> tags;
    private String title;
}
