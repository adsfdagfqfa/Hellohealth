package com.hellohealth.entity.returnParam;

import lombok.Data;

@Data
public class Result<T> {
    private String msg;
    private int errorCode;
    private T data;
    public Result<T> isSuccess() {
        this.errorCode = 200;
        this.msg = "success";
        return this;
    }

    public Result<T> isSuccess(T data) {
        this.errorCode = 200;
        this.msg = "success";
        this.data = data;
        return this;
    }

    public Result<T> isFail(String msg) {
        this.errorCode = 500;
        this.msg = msg;
        return this;
    }

    public Result<T> isFail(String msg, int code) {
        this.errorCode = code;
        this.msg = msg;
        return this;
    }
}
