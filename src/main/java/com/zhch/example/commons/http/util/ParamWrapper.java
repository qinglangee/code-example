package com.zhch.example.commons.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ParamWrapper {

	List<NameValuePair> params;

	private ParamWrapper(String name, String value) {
		params = new ArrayList<>();
		NameValuePair pair = new BasicNameValuePair(name, value);
		params.add(pair);
	}

	public ParamWrapper() {
		params = new ArrayList<>();
	}

	/**
	 * 创建一个参数列表，并添加第一个参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public static ParamWrapper create(String name, String value) {
		ParamWrapper wrapper = new ParamWrapper(name, value);
		return wrapper;
	}

	public List<NameValuePair> get() {
		return params;
	}

	public void put(String name, String value) {
		NameValuePair pair = new BasicNameValuePair(name, value);
		params.add(pair);
	}

	/**
	 * 创建map, 并添加第一个参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public static Map<String, String> createMap(String name, String value) {
		Map<String, String> map = new HashMap<>();
		map.put(name, value);
		return map;
	}

}
