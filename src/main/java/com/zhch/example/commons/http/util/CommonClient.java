package com.zhch.example.commons.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonClient {
	Logger LOG = LoggerFactory.getLogger(CommonClient.class);

	protected CloseableHttpClient client;
	protected boolean debug = false;

	CookieStore cookieStore;

	private String redirecHost;

	public CommonClient() {
		client = customClient();
	}

	/**
	 * 需要自定义的 client 时
	 * 
	 * @return
	 */
	public CloseableHttpClient customClient() {
		cookieStore = new BasicCookieStore();
		// Use custom credentials provider if necessary.
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
				.setDefaultCredentialsProvider(credentialsProvider).setDefaultRequestConfig(defaultRequestConfig)
				.build();
		return httpclient;
	}

	public String requestGet(String url) {
		return requestGet(url, false);
	}

	public String requestGet(String url, boolean sendRedirect) {
		return requestGet(url, null, sendRedirect);
	}

	public String requestGet(String url, Map<String, String> headers, boolean sendRedirect) {
		if (url == null) {
			return "";
		}
		HttpGet httpget = new HttpGet(url);
		if (headers != null) {
			for (Entry<String, String> e : headers.entrySet()) {
				httpget.setHeader(e.getKey(), e.getValue());
			}
		}

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {

				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else if (status == 302) { // 302的重新处理
					LOG.debug("get retrive 302: " + response.getFirstHeader("Location").getValue());
					if (sendRedirect) {
						// TODO ZHCH 设置 host
						String redirectUrl = response.getFirstHeader("Location").getValue();
						if (!redirectUrl.startsWith("http")) {
							redirectUrl = "http://" + getRedirecHost() + redirectUrl;
						}
						LOG.debug("GET redirectUrl:" + redirectUrl);
						Map<String, String> headers = new HashMap<>();
						headers.put("Referer", url);
						return requestGet(redirectUrl, headers, sendRedirect);
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		String responseBody = "";
		try {
			responseBody = client.execute(httpget, responseHandler);
		} catch (IOException e) {
			LOG.error("get 请求内容出错。url:" + url, e);
		}
		return responseBody;
	}

	public String requestPost(String url, List<NameValuePair> params) {
		return requestPost(url, params, false);
	}

	public String requestPost(String url, List<NameValuePair> params, boolean sendRedirect) {
		return requestPost(url, params, null, sendRedirect);
	}

	public String requestPost(String url, List<NameValuePair> params, Map<String, String> headers,
			boolean sendRedirect) {
		HttpPost post = new HttpPost(url);
		if (params != null) {
			try {
				post.setEntity(new UrlEncodedFormEntity(params, "utf8"));
			} catch (UnsupportedEncodingException e) {
				LOG.error("不可能发生的编码错误发生了。。。。", e);
			}
		}
		if (headers != null) {
			for (Entry<String, String> e : headers.entrySet()) {
				post.setHeader(e.getKey(), e.getValue());
			}
		}

		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();

				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else if (status == 302) { // 302的重新处理
					LOG.debug("post retrive 302: " + response.getFirstHeader("Location").getValue());
					if (sendRedirect) {
						// TODO ZHCH 设置 host
						String redirectUrl = response.getFirstHeader("Location").getValue();
						if (!redirectUrl.startsWith("http")) {
							redirectUrl = "http://" + getRedirecHost() + redirectUrl;
						}
						LOG.debug("POST redirectUrl:" + redirectUrl);
						Map<String, String> headers = new HashMap<>();
						headers.put("Referer", url);
						return requestGet(redirectUrl, headers, sendRedirect);
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		String responseBody = "";
		try {
			responseBody = client.execute(post, responseHandler);
		} catch (IOException e) {
			LOG.error("post 请求出错了。url:" + url, e);
		}
		return responseBody;
	}

	public byte[] requestImg(String url) {
		HttpGet get = new HttpGet(url);

		// Create a custom response handler
		ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>() {

			@Override
			public byte[] handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();

				if (status >= 200 && status < 300) {
					InputStream in = response.getEntity().getContent();
					List<Byte> list = new ArrayList<>();
					int b = -1;
					while ((b = in.read()) != -1) {
						list.add((byte) b);
					}
					byte[] result = new byte[list.size()];
					for (int i = 0; i < list.size(); i++) {
						result[i] = list.get(i);
					}
					return result;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
		byte[] responseBody;
		try {
			responseBody = client.execute(get, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return responseBody;
	}

	public String getRedirecHost() {
		return redirecHost;
	}

	public void setRedirecHost(String redirecHost) {
		this.redirecHost = redirecHost;
	}
}
