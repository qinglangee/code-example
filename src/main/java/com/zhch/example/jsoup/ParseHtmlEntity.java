package com.zhch.example.jsoup;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlEntity {

    public void test() throws IOException {
     // TODO ZHCH  用 classpath:com/zhch/example/jsoup/example.html 方式取
        String classpath = this.getClass().getResource("/").getPath();

        File file = new File(classpath + "com/zhch/example/jsoup/example.html");
        Document doc = Jsoup.parse(file, "utf-8");

        Element div = doc.getElementById("test_for_html_entity");
        Elements spans = div.getElementsByTag("span");
        for(Element span :spans){
            System.out.println("text:" + span.text());
        }
    }

    public static void main(String[] args) throws IOException {
        ParseHtmlEntity t = new ParseHtmlEntity();
        t.test();
    }
}
