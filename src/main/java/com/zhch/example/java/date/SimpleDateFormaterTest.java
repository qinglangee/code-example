package com.zhch.example.java.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * SimpleDateFormat 的文档, 有关于格式的一些介绍<br/>
 * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
 * @author zhch 2017年6月26日
 *
 */
public class SimpleDateFormaterTest {
    public void test() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(date);
        System.out.println(format.format(date));
        SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd　HH:mm:ss");
        System.out.println(longFormat.format(date));
        String dateStr = "2017-06-26　17:14:16";
        Date parseDate = longFormat.parse(dateStr);
        System.out.println(parseDate);

        // 设置locale
        String str2 = "Mon Jun 26 17:14:16 CST 2017";
        SimpleDateFormat usFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date date2 = usFormat.parse(str2);
        System.out.println(date2);
        String str3 = "星期一 六月 26 17:14:16 +0800 2017";
        SimpleDateFormat zhFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.CHINA);
        System.out.println(zhFormat.parse(str3));
        SimpleDateFormat zhFormat2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        System.out.println(zhFormat2.parse(str3));

    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormaterTest t = new SimpleDateFormaterTest();
        t.test();
    }
}
