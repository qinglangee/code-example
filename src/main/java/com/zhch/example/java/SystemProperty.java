package com.zhch.example.java;

public class SystemProperty {
	public void getProperty() {
		// 新行
		String newLine = System.getProperty("line.separator");
		System.out.println("a" + newLine + "b" + newLine + "c");
	}

	public static void main(String[] args) {
		SystemProperty t = new SystemProperty();
		t.getProperty();
	}
}
