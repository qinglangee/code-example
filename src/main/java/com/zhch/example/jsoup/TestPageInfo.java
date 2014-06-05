package com.zhch.example.jsoup;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestPageInfo {
	public void test() throws IOException {
		String url = Info.url;
		Document doc = Jsoup.connect(url)
				.data("query", "Java")
				.userAgent("Mozilla")
		        .cookie("auth", "token")
		        .timeout(10*1000)
		        .get();
				
				// 获取视频标题
			    String videoTitle = "";
			    if(doc.title() != null){
			    	videoTitle = doc.title().split("-")[0];
			    }
				
				// 获取视频描述
			    Elements eles = doc.getElementsByClass("key-info");
			    Iterator<Element>  it = eles.iterator();
			    while(it.hasNext()){
			    	
			    }
	}

	public static void main(String[] args) throws IOException {
		TestPageInfo t = new TestPageInfo();
		t.test();
	}
}
