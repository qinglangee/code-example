package com.zhch.example.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RadioExample {

	public void test() {
		String content = "<input type=\"radio\" value=\"0\" name=\"Term\" id=\"Term_0\">"
				+ "<label for=\"Term_0\">全职</label>"
				+ "<input type=\"radio\" checked=\"checked\" value=\"1\" name=\"Term\" id=\"Term_1\">"
				+ "<label for=\"Term_1\">兼职</label>";

		Document doc = Jsoup.parse(content);
		Elements select = doc.select("input[name=Term]");
		if (select != null) {
			System.out.println("size:" + select.size());
			System.out.println("val is : " + select.val());

			for (Element e : select) {
				System.out.println("check prop is :" + e.attr("checked"));
				if (e.attr("checked").equals("checked")) {
					System.out.println("check val:" + e.val());
				} else {
					System.out.println("not check");
				}
			}
		} else {
			System.out.println("is null");
		}
	}

	public static void main(String[] args) {
		RadioExample t = new RadioExample();
		t.test();
	}
}
