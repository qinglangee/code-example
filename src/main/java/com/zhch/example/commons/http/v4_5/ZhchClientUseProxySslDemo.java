package com.zhch.example.commons.http.v4_5;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.zhch.util.FileTools;

// 通过 proxy 访问https 链接
public class ZhchClientUseProxySslDemo {

	public static void proxyAccessSsl() throws Exception {
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(new File("d:\\workspace_mars\\resume_import_system\\jssecacerts"), null,
						new TrustSelfSignedStrategy())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpGet httpget = new HttpGet("https://passport.zhaopin.com/org/login");

			//24.157.37.61:8080
			HttpHost proxyHost = new HttpHost("24.157.37.61", 8080, "http");
			//			HttpHost proxyHost = new HttpHost("120.24.0.162", 80, "http");
			RequestConfig proxyConfig = RequestConfig.custom().setProxy(proxyHost).build();
			httpget.setConfig(proxyConfig);

			System.out.println("Executing request " + httpget.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				List<Byte> list = new ArrayList<>();
				BufferedInputStream bis = new BufferedInputStream(entity.getContent());
				int c;
				c = bis.read();
				while (c != -1) {
					list.add((byte) c);
					c = bis.read();
				}
				byte[] bytes = new byte[list.size()];
				int index = 0;
				for (byte b : list) {
					bytes[index++] = b;
				}
				String content = new String(bytes);
				FileTools.writeFile("d:\\temp\\d3\\result.html", content);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public static void main(String[] argss) throws Exception {
		proxyAccessSsl();
	}

}
