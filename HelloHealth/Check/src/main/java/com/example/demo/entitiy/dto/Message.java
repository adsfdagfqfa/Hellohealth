package com.example.demo.entitiy.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Message {
    private int errorCode;
    private String message;
    private Map<String, Object> data;

    // 构造函数
    public Message() {
        this.message = "";
        this.errorCode = 200;
        this.data = new HashMap<>();
    }

    // 返回 errorCode
    public int getErrorCode() {
        return errorCode;
    }

    // 设置 errorCode
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    // 返回 message
    public String getMessage() {
        return message;
    }

    // 设置 message
    public void setMessage(String message) {
        this.message = message;
    }

    // 返回 data
    public Map<String, Object> getData() {
        return data;
    }

    // 设置 data
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    // 将 Message 对象转换为 JSON 格式的字符串
    public String returnJson(int errorCodeNew) throws JsonProcessingException {
        this.setErrorCode(errorCodeNew);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 注册JavaTimeModule模块
        return objectMapper.writeValueAsString(this);
    }

    public String returnJson() throws JsonProcessingException{
        return returnJson(200);
    }

    // 嵌套的辅助类用于序列化 LocalDateTime 类型
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public static class LocalDateTimeWrapper {
        private LocalDateTime value;

        public LocalDateTimeWrapper(LocalDateTime value) {
            this.value = value;
        }

        public LocalDateTime getValue() {
            return value;
        }

        public void setValue(LocalDateTime value) {
            this.value = value;
        }
    }
}
