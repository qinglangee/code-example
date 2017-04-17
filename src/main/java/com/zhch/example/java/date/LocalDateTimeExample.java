package com.zhch.example.java.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeExample {

	public static void main(String[] args) {

		// 当前时间
		LocalDateTime today = LocalDateTime.now();
		System.out.println("当前时间=" + today);

		// 用参数输入创建
		today = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		System.out.println("当前时间=" + today);

		// 参数创建
		LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
		System.out.println("用参数创建时间=" + specificDate);


		//Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Date in IST=" + todayKolkata);


		// 1970 始的时间  Getting date from the base date i.e 01/01/1970
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
		System.out.println("01/01/1970 后第 10000 秒= " + dateFromBase);

		// 格式化时间
		String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
		System.out.println("格式化时间 : " + formatDateTime);

	}

}
