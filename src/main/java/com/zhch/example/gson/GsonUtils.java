package com.zhch.example.gson;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GsonUtils {
	public static Gson gson = new Gson();
	public static JsonParser parser = new JsonParser();
	
	/**
	 * 对象转换为字符串
	 * @param src
	 * @return
	 */
	public static String toJson(Object src){
		return gson.toJson(src);
	}
	
	/**
	 * 字符串转换为对象
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> typeOfT){
		return gson.fromJson(json, typeOfT);
	}
	
	/**
	 * 字符串转换为对象
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
		return gson.fromJson(json, typeOfT);
	}
	
	/**
	 * 解析字符串为json对象
	 * @param json
	 * @return
	 */
	public static JsonObject parseObject(String json){
		return parser.parse(json).getAsJsonObject();
	}
	
	/**
	 * 解析字符串为jsonArray对象
	 * @param json
	 * @return
	 */
	public static JsonArray parseArray(String json){
		return parser.parse(json).getAsJsonArray();
	}
}
