package com.hh.login.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultData {
    public Integer errorCode;
    public String message;
    public Map<String,Object> data = new HashMap<>();

    public ResultData(){
        this.message = "";
        this.errorCode=200;
    }

    public String ReturnJson(int errorCodeNew) {
        errorCode = errorCodeNew;
        String json = JSON.toJSONString(this);
        return json;
    }
    public String ReturnJson() {//默认为200
        errorCode = 200;
        String json = JSON.toJSONString(this);
        return json;
    }
}
