package com.zhch.example.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrettyPrint {
	public void test() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(new PrintTarget());
		System.out.println(json);
	}

	public static void main(String[] args) {
		PrettyPrint t = new PrettyPrint();
		t.test();
	}

	class PrintTarget {
		int age = 3;
		String name = "zhangsan";
	}
}
