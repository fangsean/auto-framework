package com.auto.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public class JsonUtils {
    static SerializerFeature[] FEATURE_NORMAL = new SerializerFeature[]{
            SerializerFeature.SkipTransientField, SerializerFeature.DisableCircularReferenceDetect};
    static SerializerFeature[] FEATURE_PRETTY = new SerializerFeature[]{
            SerializerFeature.PrettyFormat, SerializerFeature.SkipTransientField,
            SerializerFeature.DisableCircularReferenceDetect};

    public static String toJson(Object obj, boolean petty) {
        return petty ? JSON.toJSONString(obj, FEATURE_PRETTY) : JSON.toJSONString(obj, FEATURE_NORMAL);
    }

    public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> toObjectArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> asMap(String json) {
        Map<String, Object> attributeMap =
                StringUtils.isBlank(json) ? new HashMap<String, Object>() : (Map<String, Object>) JSON
                        .parseObject(json, Map.class);
        return attributeMap;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> asStringMap(String json) {
        Map<String, String> attributeMap =
                StringUtils.isBlank(json) ? new HashMap<String, String>() : (Map<String, String>) JSON
                        .parseObject(json, Map.class);
        return attributeMap;
    }


    public static String addAttributes(String attributes, Map<String, String> attributeMap) {
        Map<String, Object> m = asMap(attributes);
        m.putAll(attributeMap);
        return toJson(m);
    }

    public static String addAttributes(String attributes, String key, String value) {
        Map<String, Object> m = asMap(attributes);
        m.put(key, value);
        return toJson(m);
    }

    @SuppressWarnings("unchecked")
    public static Object getAttribute(String json, String key) {
        Map<String, Object> attributeMap =
                StringUtils.isBlank(json) ? new HashMap<String, Object>() : (Map<String, Object>) JSON
                        .parseObject(json, Map.class);
        return attributeMap.get(key);
    }
}
