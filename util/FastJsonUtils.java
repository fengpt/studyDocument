package com.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FastJsonUtils {

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.DisableCircularReferenceDetect
    };

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String toJson(Object o) {
        return JSON.toJSONString(o, features);
    }

    public static String toJson(Object o, String format) {
        return JSON.toJSONStringWithDateFormat(o, format, features);
    }

    public static <T> T parseJson(String json, Class<T> classType) {
        return JSON.parseObject(decodeHtmlFilter(json), classType);
    }

    public static <T> T parseJson(String json, TypeReference<T> type) {
        return JSON.parseObject(decodeHtmlFilter(json), type);
    }

    private static final Pattern P_QUOTE = Pattern.compile("&quot;");

    private static String decodeHtmlFilter(String s) {
        Matcher m = P_QUOTE.matcher(s);
        return m.replaceAll("\"");
    }

    public static Object[] parseArrayForDifferentTypes(String json, Type[] types) {
        List<Object> list = JSON.parseArray(json, types);
        return list.toArray(new Object[list.size()]);
    }

    public static JSONObject toJsonObject(String json) {
        return JSONObject.parseObject(json);
    }

    public static JSONArray toJsonArray(String json) {
        return JSONArray.parseArray(json);
    }

}
