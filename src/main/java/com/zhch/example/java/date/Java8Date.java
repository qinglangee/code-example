package com.zhch.example.java.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.zhch.util.TempLog;

/**
 * 如果是 JDK 8 的应用,可以使用 Instant 代替 Date , LocalDateTime 代替 Calendar ,
 * DateTimeFormatter 代替 Simpledateformatter<br>
 * 官方给出的解释: simple beautiful strong immutable thread - safe 。<br>
 * @author zhch 2017年5月25日
 *
 */
public class Java8Date {

    public void test() {

        parseDate();
        formatDate();
    }
    // 解析时间
    public void parseDate(){
        TempLog.logHead();
        String date0 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss a"));
        TempLog.log(date0);
        String date1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
        TempLog.log(date1);
        String date2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a"));
        TempLog.log(date2);
        String date3 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        TempLog.log(date3);
    }

    // 格式化时间
    public void formatDate(){
    }


    public static void main(String[] args) {
        Java8Date t = new Java8Date();
        t.test();
    }
}
