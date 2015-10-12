package com.zhch.example.commons.http.v4_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 4.2版本的例子
 * 
 * @author zhch
 *
 */
public class HttpClientExample {
	public void test() throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		// get 请求
		HttpGet httpGet = new HttpGet("http://targethost/homepage");

		HttpResponse response1 = httpclient.execute(httpGet);

		// The underlying HTTP connection is still held by the response object 
		// to allow the response content to be streamed directly from the network socket. 
		// In order to ensure correct deallocation of system resources 
		// the user MUST either fully consume the response content  or abort request 
		// execution by calling HttpGet#releaseConnection().

		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity1);
		} finally {
			httpGet.releaseConnection();
		}

		// post 请求
		HttpPost httpPost = new HttpPost("http://targethost/login");
		// 设置参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			httpPost.releaseConnection();
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClientExample t = new HttpClientExample();
		t.test();
	}
}
