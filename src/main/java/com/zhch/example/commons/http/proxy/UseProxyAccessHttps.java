package com.zhch.example.commons.http.proxy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * java通过代理访问外网https http://blog.csdn.net/today1858/article/details/5864644 <br>
 * 蚂蚁代理 http://www.mayidaili.com/
 * 
 * @author zhch
 *
 */
public class UseProxyAccessHttps {

	public static void main(String[] args) throws Exception {
		//设置代理
		String proxy = "127.0.0.1";
		int port = 80;
		System.setProperty("proxyType", "4");
		System.setProperty("proxyPort", Integer.toString(port));
		System.setProperty("proxyHost", proxy);
		System.setProperty("proxySet", "true");

		String url = "https://pan.baidu.com/";
		SSLContext sc = SSLContext.getInstance("SSL");

		//指定信任https
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.connect();
		System.out.println("返回结果：" + conn.getResponseMessage());

		InputStream is = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String curLine = "";
		while ((curLine = reader.readLine()) != null) {
			System.out.println(curLine);
		}
		is.close();
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}

	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
