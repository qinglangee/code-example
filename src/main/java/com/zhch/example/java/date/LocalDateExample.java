package com.zhch.example.java.date;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * copy form
 * http://www.journaldev.com/2800/java-8-date-time-api-example-tutorial-
 * localdate-instant-localdatetime-parse-and-format
 * 
 * LocalDate 是一个表示日期的不可变类， 默认格式是 yyyy-MM-dd.可以用 now() 获得当前日期。
 * 
 * @author pankaj
 *
 */
public class LocalDateExample {

	public static void main(String[] args) {

		//当前日期
		LocalDate today = LocalDate.now();
		System.out.println("Current Date=" + today);

		// 用参数构建
		LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
		System.out.println("Specific Date=" + firstDay_2014);

		//尝试错误的输入参数
		//LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
		//Exception in thread "main" java.time.DateTimeException: 
		//Invalid date 'February 29' as '2014' is not a leap year

		// Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Date in IST=" + todayKolkata);

		// 错误输入  java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
		//LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));

		// Getting date from the base date i.e 01/01/1970
		LocalDate dateFromBase = LocalDate.ofEpochDay(365);
		System.out.println("365th day from base date= " + dateFromBase);

		LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
		System.out.println("100th day of 2014=" + hundredDay2014);
		
		// 格式化日期, uuuu 和 yyyy 都表示年
		System.out.println("格式化日期 ： " + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
		System.out.println("格式化日期 ： " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}

}