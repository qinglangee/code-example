package com.zhch.example.encrypt;

import java.util.Base64;

public class Base64Test {

	public void test() {
		String src = "base64src";
		String code = Base64.getEncoder().encodeToString(src.getBytes());
		System.out.println(src + ":" + code);

		String decode = new String(Base64.getDecoder().decode(code));
		System.out.println(decode + ":" + code);

	}
	

	public static void main(String[] args) {
		Base64Test t = new Base64Test();
		t.test();
	}
}
