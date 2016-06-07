package com.zhch.example.java;

public class UnicodeString {

	public static String toUnicode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append("\\u").append(Integer.toHexString(str.charAt(i)));
		}
		return sb.toString();
	}

	public void pritnUnicode(String str) {
		System.out.println(toUnicode(str));
	}

	public static void main(String[] args) {
		UnicodeString t = new UnicodeString();
		t.pritnUnicode("耿永辉    ");
	}
}
