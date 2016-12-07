package com.zhch.example.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Java8Date {

	public void test() {

		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss a"));
		System.out.println(date);

	}

	public static void main(String[] args) {
		Java8Date t = new Java8Date();
		t.test();
	}
}
