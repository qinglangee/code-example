package com.zhch.example.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SelectorExample {
    String htmlStr = "<html>                                                    "
            + "<body>                                                             "
            + "    <div id='getAbsUrl'>                                                     "
            + "        <a class='first' href='/user.html'>点我，我是链接</a>                                                     "
            + "        <a class='second' href='//user.html'>点我，我是链接</a>                                                     "
            + "        <a class='third' href='//www.here.com/user.html'>点我，我是链接</a>                                                     "
            + "    </div>                                                     "
            + "</body>                                                           "
            + "</html>                                                                     ";

    public void test() {

    }

    // 取绝对 url
    public void getAbsUrl() {
        System.out.println("test getAbsUrl ==========================================================");
        Document doc = Jsoup.parse(htmlStr, "http://www.baidu.com/gethaha.html");
        Elements links = doc.select("#getAbsUrl a");
        for(Element link : links){
            String absHref = link.attr("abs:href");
            System.out.println("link url is:" + absHref);
        }
    }

    public static void main(String[] args) {
        SelectorExample t = new SelectorExample();
        t.getAbsUrl();
    }

}
