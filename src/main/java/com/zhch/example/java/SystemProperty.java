package com.zhch.example.java;

public class SystemProperty {
	public void getProperty() {
		// 新行
		String newLine = System.getProperty("line.separator");
		System.out.println("line.separator a" + newLine + "b" + newLine + "c");

		// 路径分隔
		String fileSep = System.getProperty("file.separator");
		System.out.println("file.separator a" + fileSep + "b" + fileSep + "c");

		// 文件编码
		System.out.println("file.encoding:" + System.getProperty("file.encoding"));

		// 程序目录
		System.out.println("user.dir:" + System.getProperty("user.dir"));
	}

	public static void main(String[] args) {
		SystemProperty t = new SystemProperty();
		t.getProperty();
	}
}
