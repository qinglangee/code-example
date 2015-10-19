package com.zhch.example.commons.http.v4_5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ZhchBasicUse {

	/**
	 * 这是推荐的使用方法<br>
	 * 使用 handler 来处理 response, 确保完事后连接会被释放
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void get() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://httpbin.org/");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 这是一些其它的使用方法
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void test() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

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
			httpGet.reset();
		}

		// post 请求
		HttpPost httpPost = new HttpPost("http://targethost/login");
		// 设置参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", "vip"));
		params.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
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
}
