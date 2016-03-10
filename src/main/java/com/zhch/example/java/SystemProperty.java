package com.zhch.example.java;

public class SystemProperty {
	public void getProperty() {
		// 新行
		String newLine = System.getProperty("line.separator");
		System.out.println("line.separator a" + newLine + "b" + newLine + "c");

		// 路径分隔
		String fileSep = System.getProperty("file.separator");
		System.out.println("file.separator a" + fileSep + "b" + fileSep + "c");
	}

	public static void main(String[] args) {
		SystemProperty t = new SystemProperty();
		t.getProperty();
	}
}
