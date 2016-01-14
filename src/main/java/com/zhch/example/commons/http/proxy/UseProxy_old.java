package com.zhch.example.commons.http.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class UseProxy_old {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String content = httpGet();
		System.out.println("返回内容：" + content);
	}

	/**
	 * java使用代理发送http请求<br>
	 * 旧的方法，使用了 deprecated 方法 httpclient.getParams()
	 * 
	 * @return
	 */
	public static String httpGet() {
		String ip = "218.106.96.211";
		String content = null;
		DefaultHttpClient httpclient = null;
		try {
			httpclient = new DefaultHttpClient();
			/** 设置代理IP **/
			HttpHost proxy = new HttpHost(ip, 80);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

			HttpGet httpget = new HttpGet("http://www.ip.cn/");

			httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 5); //设置请求超时时间  
			httpget.setHeader("Proxy-Authorization", "Basic eXVsb3JlOll1bG9yZVByb3h5MjAxMzo=");
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
			httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

			HttpResponse responses = httpclient.execute(httpget);
			HttpEntity entity = responses.getEntity();
			content = EntityUtils.toString(entity, "utf8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown(); //关闭连接  
		}
		return content;
	}
}
