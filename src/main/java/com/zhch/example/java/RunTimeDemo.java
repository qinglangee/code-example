package com.zhch.example.java;

import java.io.IOException;

public class RunTimeDemo {
	public void test() throws InterruptedException, IOException {
		Process process = Runtime.getRuntime()
				.exec("tesseract d:\\temp\\d3\\tesseract\\data\\2288.gif d:\\temp\\d3\\tesseract\\out -l zhi");
		process.waitFor();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		RunTimeDemo t = new RunTimeDemo();
		t.test();
		System.out.println("over!!");
	}
}
