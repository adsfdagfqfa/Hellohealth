package com.example.micro.model;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Message {
    public int errorCode; //错误码，用于判断本次操作是否成功
    public String message;
    // public bool status ;   //操作是否成功
    public Map<String, Object> data; //返回数据

    //初始化
    public Message() {
        message = "";
        errorCode = 200;//默认200,若后端没接收到、无法处理，则code=300
        data = new HashMap<>();
    }

    //将类以json字符串的形式传递给前端
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
