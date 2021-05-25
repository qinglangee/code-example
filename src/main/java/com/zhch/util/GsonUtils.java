package com.zhch.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {
    private static Gson gson = new Gson();
    private static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    private static JsonParser parser = new JsonParser();

    /**
     * 对象转换为字符串
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        if (src == null) {
            return null;
        }
        return gson.toJson(src);
    }

    /**
     * 打印可读格式的json
     *
     * @param src
     * @return
     */
    public static String toPretty(Object src) {
        if (src == null) {
            return null;
        }
        return prettyGson.toJson(src);
    }

    /**
     * 字符串转换为对象
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJson(String json, Class<T> typeOfT) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    /**
     * json element 转换为对象
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJson(JsonElement json, Class<T> typeOfT) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 字符串转换为对象
     * Type type = new TypeToken<List<People>>(){}.getType();
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    /**
     * json element 转换为对象
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 解析字符串为json对象
     *
     * @param json
     * @return
     */
    public static JsonObject parseObject(String json) {
        if (json == null) {
            return null;
        }
        return parser.parse(json).getAsJsonObject();
    }

    /**
     * 解析字符串为jsonArray对象
     *
     * @param json
     * @return
     */
    public static JsonArray parseArray(String json) {
        if (json == null) {
            return null;
        }
        return parser.parse(json).getAsJsonArray();
    }

    /**
     * 取String 属性， 没有就返回空
     */
    public static String getString(JsonObject json, String attr) {
        return json != null && json.has(attr) ? json.get(attr).getAsString() : null;
    }

    /**
     * 取String 属性， 没有就返回空
     */
    public static Long getLong(JsonObject json, String attr) {
        return json != null && json.has(attr) ? json.get(attr).getAsLong() : null;
    }

    /**
     * 取String 属性， 没有就返回空
     */
    public static Integer getInt(JsonObject json, String attr) {
        return json != null && json.has(attr) ? json.get(attr).getAsInt() : null;
    }

    /**
     * 取string属性，如果属性名有"."分隔,就深入查找
     * @param json
     * @param attr
     * @return
     */
    public static String searchString(JsonObject json, String attr) {
        SearchResult preObj = searchPreObject(json, attr);
        return getString(preObj.json, preObj.lastAttr);
    }

    /**
     * 取 JsonObject 属性，如果属性名有"."分隔,就深入查找
     * @param json
     * @param attr
     * @return
     */
    public static JsonArray searchArray(JsonObject json, String attr) {
        SearchResult preObj = searchPreObject(json, attr);
        JsonObject res = preObj.json;
        String name = preObj.lastAttr;
        return res != null && res.has(name) ? res.get(name).getAsJsonArray() : null;
    }

    /**
     * 如果attr中有"."分隔，取最后一个点之前部分对应的JsonObject<br>
     * json={a:{b:{c:{d:1}}}}, attr=a.b.c 返回 {json:{c:{d:1}}, lastAttr:"c"}
     * @return
     */
    private static SearchResult searchPreObject(JsonObject json, String attr) {
        if (json == null || attr == null) {
            return null;
        }
        if (json == null || attr == null || !attr.contains(".")) {
            return new SearchResult(json, attr);
        }

        String[] strs = attr.split("\\.");
//        for(String str : strs){
//            System.out.println("att is :" + str);
//        }
        if (strs.length < 2) {
            return new SearchResult(json, attr);
        }
        JsonObject result = json;
        for (int i = 0; i < strs.length - 1; i++) {
            if (result.has(strs[i])) {
                result = result.get(strs[i]).getAsJsonObject();
            } else {
                return new SearchResult(null, null);
            }
        }
        return new SearchResult(result, strs[strs.length - 1]);
    }

    /**
     * 取 JsonObject 属性，如果属性名有"."分隔,就深入查找
     * @param json
     * @param attr
     * @return
     */
    public static JsonObject searchObj(JsonObject json, String attr) {
        SearchResult preObj = searchPreObject(json, attr);
        JsonObject res = preObj.json;
        String name = preObj.lastAttr;
        return res != null && res.has(name) ? res.get(name).getAsJsonObject() : null;
    }

}

class SearchResult {
    public JsonObject json;
    public String lastAttr;

    public SearchResult(JsonObject json, String lastAttr) {
        this.json = json;
        this.lastAttr = lastAttr;
    }
}
