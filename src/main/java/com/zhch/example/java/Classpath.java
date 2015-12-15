package com.zhch.example.java;

import java.net.URL;

public class Classpath {
	/**
	 * 打印 classpath
	 */
	public void printClasspath() {
		String classpathStr = System.getProperty("java.class.path");
		System.out.println("java.class.path : " + classpathStr);
	}

	public void resourcePath() {
		// 路径以 / 开头
		URL url = Classpath.class.getResource("/com/zhch");
		// 打印 classes 目录  : /home/lifeix/workspace_kepler/code-example/target/classes/
		System.out.println("Xxx.class.getResource() : " + url.getPath());
	}

	public void loaderClassPath() {
		// 通过这个类的 class loader 来获取资源， 在 test 类中调用方法时访问到功能类所在的路径
		// 路径 不 以 / 开头
		URL url = Classpath.class.getClassLoader().getResource("com/zhch");
		System.out.println("Xxx.class.getClassLoader().getResource(\"\") : " + url.getPath());
	}

	public static void main(String[] args) {
		Classpath t = new Classpath();
		t.printClasspath();
		t.resourcePath();
		t.loaderClassPath();

		// TODO ZHCH javatool   classpath util
	}
}
