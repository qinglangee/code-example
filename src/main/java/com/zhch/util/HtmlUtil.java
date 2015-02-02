package com.zhch.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 对http地址进行解析工具类
 * @author pengchuang
 *
 */
public class HtmlUtil {
	
	final static Logger LOG = LoggerFactory.getLogger(HtmlUtil.class);

	//请求的超时时间10秒
	private final static int WAIT_TIME = 10000;
	
	//字符集
	private final static String CHAR_SET = "UTF8";

	/**
	 * @param httpUrl
	 * @return html代码
	 */ 
	public static String getHtmlText(String httpUrl) {
	   StringBuffer htmlText = new StringBuffer();
	   InputStream in = null;
	   BufferedReader breader = null;
	   try {
	    URL url = new java.net.URL(httpUrl);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8");
	    connection.setConnectTimeout(WAIT_TIME);
	    connection.connect();
	    in = connection.getInputStream();
	    breader = new BufferedReader(new InputStreamReader(in, CHAR_SET));
	    String currentLine;
	    while ((currentLine = breader.readLine()) != null) {
	    	htmlText.append(currentLine);
	    }
	    return htmlText.toString();
	   } catch (Exception e) {
		   return null;
	   } finally {
		   if(in != null){
			   try {
				 in.close();
				} catch (IOException e) {
				}
		   }
		   if(breader != null){
			   try {
				   breader.close();
				} catch (IOException e) {
				}
		   }
	   }
	}
	
	/**
	 * 使用post请求来取得网络文本
	 * @param httpUrl
	 * @param params
	 * @return
	 */
	public static String postHtmlText(String httpUrl, Map<String, String> params) {
		StringBuffer htmlText = new StringBuffer();
		InputStream in = null;
		BufferedReader breader = null;
        try {
    		URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8");
	        conn.setConnectTimeout(WAIT_TIME);
	        conn.setDoOutput(true);// 是否输入参数
	        if(params != null){
		        StringBuffer paramsStr = new StringBuffer();
		        for (Map.Entry<String, String> entry : params.entrySet()) {
		        	paramsStr.append(entry.getKey());
		        	paramsStr.append('=');
					if (entry.getValue() != null) {
						paramsStr.append(entry.getValue());
					}
					paramsStr.append('&');
				}
		        // 表单参数与get形式一样
		        byte[] bypes = paramsStr.toString().getBytes();
		        conn.getOutputStream().write(bypes);// 输入参数
	        }
	        in = conn.getInputStream();
	        breader = new BufferedReader(new InputStreamReader(in, CHAR_SET));
		    String currentLine;
		    while ((currentLine = breader.readLine()) != null) {
		    	htmlText.append(currentLine);
		    }
		    return htmlText.toString();
        } catch (Exception e) {
        	return null;
        } finally {
        	if(in != null){
 			   try {
 				 in.close();
 			   } catch (IOException e) {
 			   }
 		   }
		   if(breader != null){
			   try {
				   breader.close();
				} catch (IOException e) {
				}
		   }
 	   }
	}
	
	public static String getHtmlTextAddSSL(String httpUrl) {
		HttpClient client = new DefaultHttpClient();
		HttpEntity entity = null;
		try{ 
			Protocol myhttps = new Protocol("https", new DefaultProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
			HttpGet httpGet = new HttpGet(httpUrl.toString());
			client.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
			HttpResponse response = client.execute(httpGet);
			entity = response.getEntity();
			if (entity == null) {
				throw new Exception("Http response content is null.");
			}
			byte[] bytes = EntityUtils.toByteArray(entity);
			return new String(bytes, "UTF-8");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		} finally {
			if(entity != null){
				try {
					entity.getContent().close();
				} catch (Exception e) {
				}	
			}
			// close client
			client.getConnectionManager().shutdown();
		}
	
	}
	
	/**
	 * 
	 * @param httpUrl
	 * @return html缓冲流
	 */
	public static InputStream getHtmlStream(String httpUrl){
		try {
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8");
		    connection.setConnectTimeout(WAIT_TIME);
		    connection.connect();
		    InputStream in = connection.getInputStream();
			return in;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		} 
	}
	
	
}

