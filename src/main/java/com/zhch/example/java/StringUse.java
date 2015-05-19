package com.zhch.example.java;

public class StringUse {
	public void test() {
		// String format
		// %s 字符串类型 "mingrisoft"
		// %c 字符类型 'm'
		// %b 布尔类型 true
		// %d 整数类型（十进制） 99
		String result = String.format("I am a%speople", " nice ");
		System.out.println(result);
	}

	public static void main(String[] args) {
		StringUse t = new StringUse();
		t.test();
	}
}
