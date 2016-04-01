package com.zhch.example.java8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionSyntax {

	public void listForEach() {
		System.out.println("list for each ======================");
		List<String> list = new ArrayList<>();
		list.add("str01");
		list.add("str02");
		list.add("str03");
		list.forEach((line) -> System.out.println("all:" + line));
		list.forEach((str) -> {
			if (str.endsWith("01")) {
				System.out.println("first:" + str);
			} else {
				System.out.println("other:" + str);
			}
		});
	}

	public void mapForEach() {
		System.out.println("map for each ==========================");
		Map<String, String> map = new HashMap<>();
		map.put("k01", "v01");
		map.put("k02", "v02");
		map.put("k03", "v03");
		map.forEach((k, v) -> {
			if (k.endsWith("01")) {
				System.out.println("first:" + v);
			} else {
				System.out.println("other:" + v);
			}
		});
	}

	public static void main(String[] args) {
		CollectionSyntax t = new CollectionSyntax();
		t.listForEach();
		t.mapForEach();
	}
}
