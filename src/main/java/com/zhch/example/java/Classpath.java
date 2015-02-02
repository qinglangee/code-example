package com.zhch.example.java;

import java.net.URL;

public class Classpath {
	/**
	 * 打印 classpath
	 */
	public void printClasspath() {
		String classpathStr = System.getProperty("java.class.path");
		System.out.print(classpathStr);
	}
	
	public void resourcePath(){
		URL url = Classpath.class.getResource("/");
		// 打印 classes 目录  : /home/lifeix/workspace_kepler/code-example/target/classes/
		System.out.println(url.getPath());
	}

	public static void main(String[] args) {
		Classpath t = new Classpath();
		t.resourcePath();
		
		// TODO ZHCH javatool   classpath util
	}
}
