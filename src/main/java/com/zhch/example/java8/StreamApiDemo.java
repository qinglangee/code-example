package com.zhch.example.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamApiDemo {
	List<String> nameList = new ArrayList<>();
	List<Integer> intList = new ArrayList<>();

	public StreamApiDemo() {
		nameList.add("001name");
		nameList.add("102name");
		nameList.add("003name");
		nameList.add("004name");
		nameList.add("005name");

		intList.add(3);
		intList.add(5);
		intList.add(8);
		intList.add(6);
		intList.add(7);
		intList.add(4);
		intList.add(2);
	}
	
	public void sort() {
		System.out.println("sort===========================");
		List<String> defaultSort = nameList.stream().sorted().collect(Collectors.toList());
		List<String> customSort = nameList.stream().sorted((o1,o2)->{return o1.charAt(2) - o2.charAt(2);}).collect(Collectors.toList());
		List<String> reverseSort = nameList.stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
		System.out.println(nameList);
		System.out.println(defaultSort);
		System.out.println(customSort);
		System.out.println(reverseSort);
		
	}

	public void filter() {
		System.out.println("filter===========================");
		List<String> first3name = nameList.stream().filter(name -> name.startsWith("00")).limit(3)
				.collect(Collectors.toList());

		System.out.println(first3name);
	}

	public void matching() {
		System.out.println("matching ======================================");
		boolean start00 = nameList.stream().allMatch(name -> name.startsWith("00"));
		System.out.println("start with 00 :" + start00);
	}

	public void finding() {
		System.out.println("finding =========================================");
		Optional<String> op1 = nameList.stream().findFirst();
		Optional<String> op2 = nameList.stream().findFirst();
		System.out.println("first:" + op1 + " any:" + op2);
	}

	public void mapping() {
		System.out.println("mapping =========================================");
		List<String> f3 = nameList.stream().map(name -> name.substring(0, 3)).collect(Collectors.toList());
		List<String> f4 = nameList.stream().map(String::toString).collect(Collectors.toList());
		System.out.println("f3:" + f3);
		System.out.println("f4:" + f4);
	}

	public void reducing() {
		System.out.println("reducing ========================================");
		String result = nameList.stream().reduce("balaba:", (a, b) -> a + b);
		System.out.println(result);
		Integer max = intList.stream().reduce(Integer.MIN_VALUE, Integer::max);
		System.out.println("max int:" + max);
	}

	public static void main(String[] args) {
		StreamApiDemo t = new StreamApiDemo();
		t.sort();
		t.filter();
		t.matching();
		t.finding();
		t.mapping();
		t.reducing();
	}
}
