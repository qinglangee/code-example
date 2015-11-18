package com.zhch.example.date;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * LocalTime Examples
 * 
 * @author pankaj
 *
 */
public class LocalTimeExample {

	public static void main(String[] args) {

		// 只表示时间
		LocalTime time = LocalTime.now();
		System.out.println("Current Time=" + time);

		//参数创建
		LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
		System.out.println("Specific Time of Day=" + specificTime);

		// 错误输入
		//LocalTime invalidTime = LocalTime.of(25,20);
		//Exception in thread "main" java.time.DateTimeException: 
		//Invalid value for HourOfDay (valid values 0 - 23): 25

		//Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Time in IST=" + timeKolkata);

		// 错误输入  java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
		//LocalTime todayIST = LocalTime.now(ZoneId.of("IST"));

		//Getting date from the base date i.e 01/01/1970
		LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
		System.out.println("10000th second time= " + specificSecondTime);

	}

}