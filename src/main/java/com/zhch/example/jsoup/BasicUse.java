package com.zhch.example.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BasicUse {
	/**
	 * 简单示例
	 * @throws IOException 
	 */
	public void findElement() throws IOException {
		Document doc = Jsoup.connect("http://www.baidu.com/").get();
		System.out.println(doc.title());
		Elements links = doc.select("#u1 a");
		for(Element e : links){
			System.out.println(e.text());
		}
		// 取第一个
		Element first = links.first();
		// 取属性的值
		System.out.println("first href: " + first.attr("href"));
	}
	
	/**
	 * 设置 cookie 值
	 */
	public void withCookie() throws IOException {
		Connection conn = Jsoup.connect("http://www.l99.com/dashboardPage.action?dt=5");
		// 取 cookie
		Map<String, String> cookies = conn.execute().cookies();
		// 设置 cookie
		conn = Jsoup.connect("http://www.l99.com/dashboardPage.action?dt=5");
		conn.cookie("name", "value");
		conn.cookies(cookies);
		Document doc = conn.get();
		System.out.println(doc.title());
		
		// 设置忽略 ContentType  org.jsoup.UnsupportedMimeTypeException
		conn = Jsoup.connect("http://www.l99.com/dashboardPage.action?dt=5").ignoreContentType(true);
		doc = conn.get();
		System.out.println(doc.title());
	}
	
	/**
	 * 从字符串获取内容
	 */
	public void parseString() {
		String html = "<html><head><title>First parse</title></head>"
			+ "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		// 打印 title
		System.out.println(doc.title());
	}
	
	/**
	 * 从文件获取内容
	 * @throws IOException 
	 */
	public void parseFile() throws IOException {
		// TODO ZHCH  用 classpath:com/zhch/example/jsoup/example.html 方式取
		String classpath = this.getClass().getResource("/").getPath();
		
		File file = new File(classpath + "com/zhch/example/jsoup/example.html"); 
		Document doc = Jsoup.parse(file, "utf-8");
		// 打印 title
		System.out.println(doc.title());
	}

	public static void main(String[] args) throws Exception {
		BasicUse t = new BasicUse();
		t.findElement();
//		t.parseFile();
	}
}
