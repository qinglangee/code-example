package com.zhch.example.commons.http.proxy.collect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.plexus.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProxyCollectMain {

	static Pattern P_class = Pattern.compile("(\\.(.+?)\\{.*?\\})+?");
	String outputFile = "d:\\temp\\d3\\output.txt";
	String EOL = System.getProperty("line.separator");
	public static void main(String[] args) {
		ProxyCollectMain t = new ProxyCollectMain();
		for (int i = 1; i < 1000; i++) {
			System.out.println("get from page :" + i);
			t.getFromPage(i);
		}

	}

	public void getFromPage(int pageNum) {
		String url = "http://www.mayidaili.com/free/" + pageNum;
		List<ProxyBean> proxies = getProxys(url);
		if (proxies == null) {
			System.out.println("list is null.");
			return;
		}
		List<String> results = new ArrayList<>();
		for (ProxyBean proxy : proxies) {
			long timeStart = System.currentTimeMillis();
			if (ProxyUtils.canTunnel(proxy.host, proxy.port)) {
				long time = System.currentTimeMillis() - timeStart;
				System.out.println("vvvvvvvvvvvvvvvvvv " + time + " " + proxy);
				String proxyStr = proxy.host + ":" + proxy.port + ":" + time;
				if (StringUtils.isNotBlank(proxy.type)) {
					proxyStr = proxyStr + ":" + proxy.type;
				}
				results.add(proxyStr);
			} else {
				System.out.println("xx " + proxy);
			}
		}

		for (String line : results) {
			try {
				FileUtils.fileAppend(outputFile, line + EOL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<ProxyBean> getProxys(String url) {

		List<ProxyBean> list = new ArrayList<>();
		try {
			String html = getHtml(url);
			Document doc = Jsoup.parse(html);
			Elements trs = doc.select(".col-md-9 table tbody tr");
			for (Element tr : trs) {
				Elements tds = tr.getElementsByTag("td");
				if (tds == null || tds.size() < 2) {
					continue;
				}
				String host = tds.get(0).text();
				String port = getPortFromElement(tds.get(1));
				String type = tds.get(2).text();
				//				System.out.println("proxy - " + host + ":" + port + " " + type);
				list.add(new ProxyBean(host, port, type));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private static String getPortFromElement(Element td) {
		String styleText = td.getElementsByTag("style").first().html();

		//		System.out.println("===" + styleText);
		Map<String, String> blocks = new HashMap<>();
		Matcher m = P_class.matcher(styleText);
		while (m.find()) {
			String style = m.group();
			if (style.contains("block")) {
				blocks.put(m.group(2), "1");
				//				System.out.println(m.group(2));
			}
		}
		Elements portEs = td.getElementsByTag("span");
		for (Element portE : portEs) {
			String htmlClass = portE.attr("class");
			if (blocks.get(htmlClass) != null) {
				return portE.text();
			}
		}

		return null;
	}

	public static String getHtml(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			//			System.out.println("Executing request " + httpget.getRequestLine());

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
			return responseBody;
		} finally {
			httpclient.close();
		}
	}
}
