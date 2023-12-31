package com.hl.hhuserinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor // 包含所有类属性的构造方法
public class ResultData {

    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;
    private Integer code;

    public static ResultData ok(){
        return new ResultData(true, null, null, null,200);
    }
    public static ResultData ok(Object data){
        return new ResultData(true, null, data, null,200);
    }
    public static ResultData ok(List<?> data, Long total){
        return new ResultData(true, null, data, total,200);
    }
    public static ResultData fail(String errorMsg){
        return new ResultData(false, errorMsg, null, null,400);
    }
    public static ResultData notFound(String msg){
        return new ResultData(false,msg,null,null,404);
    }
}