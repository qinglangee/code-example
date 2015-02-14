package com.zhch.example.guava.string;

import com.google.common.base.Charsets;

public class UseCharsets {
	public void test() {
		// 字符集类型
		byte[] bytes2 = "foobarbaz".getBytes(Charsets.UTF_8);
		System.out.println(bytes2);
	}

	public static void main(String[] args) {
		UseCharsets t = new UseCharsets();
		t.test();
	}
}
