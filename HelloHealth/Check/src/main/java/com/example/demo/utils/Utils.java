package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public String getJson(String inStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个对象并设置其属性
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("type", "doc");

        ArrayNode contentArray = rootNode.putArray("content");
        ObjectNode paragraphNode = contentArray.addObject();

        paragraphNode.put("type", "paragraph");

        ArrayNode textArray = paragraphNode.putArray("content");
        ObjectNode textNode = textArray.addObject();

        textNode.put("type", "text");
        textNode.put("text", inStr);

        // 将对象转化为 JSON 字符串
        String jsonString = objectMapper.writeValueAsString(rootNode);

        System.out.println("获得的内容为：" + jsonString);
        return jsonString;
    }
}
