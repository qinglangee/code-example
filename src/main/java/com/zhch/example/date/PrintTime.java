package com.zhch.example.date;

import java.sql.Timestamp;
import java.util.Date;

public class PrintTime {

	public void miliseconds() {
		System.out.println("miliseconds to date ======================");
		long t = 1468395488913L;
		Date date1 = new Date(t);
		Date date2 = new Date(t * 1000);
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(new Timestamp(date1.getTime()));
	}

	public static void main(String[] args) {
		PrintTime t = new PrintTime();
		t.miliseconds();
	}
}
