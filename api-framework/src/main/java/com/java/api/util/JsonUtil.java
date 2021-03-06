package com.java.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.api.json.CustomObjectMapper;

/**
 * JSON 工具类
 *
 * @author huangyong
 * @since 1.0.0
 */
public final class JsonUtil {

    private static ObjectMapper objectMapper = new CustomObjectMapper();

    /**
     * 将 POJO 对象转为 JSON 字符串
     */
    public static <T> String toJson(T pojo) {
        String json;
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 字符串转为 POJO 对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
