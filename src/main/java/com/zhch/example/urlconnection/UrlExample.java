package com.zhch.example.urlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlExample {
	
	
	/**
	 * 组装get的参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String params(Map<String, String> params, String charset) throws UnsupportedEncodingException{
		if(params == null) return "";
		StringBuilder sb = new StringBuilder();
		for(String name : params.keySet()){
			sb.append(name).append("=").append(URLEncoder.encode(params.get(name), charset));
		}
		return sb.toString();
	}
	
	public void httpGet() throws MalformedURLException, IOException {
		String url = "http://www.baidu.com/s";
		String charset = "UTF-8";
		Map<String, String> map = new HashMap<String, String>();
		map.put("wd", "中国");
		String query = params(map, "utf-8");
		URLConnection connection = new URL(url + "?" + query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(response));
		String line = br.readLine();
		while(line != null){
			System.out.println(line);
			line = br.readLine();
		}
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		UrlExample t = new UrlExample();
		t.httpGet();
	}
}
