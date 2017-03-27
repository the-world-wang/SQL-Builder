package com.wang.sql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Created by paopao on 17/1/15.
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static TypeReference<Map<String, Object>> mapRefer = new TypeReference<Map<String, Object>>() {
    };

    public static Map<String, Object> convertToMap(Object obj) {
        return JsonUtils.mapper.convertValue(obj, mapRefer);
    }
}
