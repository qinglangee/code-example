package com.zhch.example.java8;

import java.util.ArrayList;
import java.util.List;

public class LambdaDemo {
	List<String> srcList = new ArrayList<>();

	public LambdaDemo() {
		srcList.add("001name");
		srcList.add("002age");
		srcList.add("003school");
		srcList.add("004parent");
		srcList.add("005girl");
		srcList.add("006ls");
	}

	// 行为参数化001：一个接口作为参数，定义行为
	interface Choose {
		boolean test(String str);
	}

	// 行为参数化002：一个方法， 可以传入行为参数
	List<String> useChoose(List<String> list, Choose choose) {
		List<String> result = new ArrayList<>();
		for (String str : list) {
			if (choose.test(str)) {
				result.add(str);
			}
		}
		return result;
	}

	// 行为参数化003：来来， 调用一下
	void testBehaviorParameterization() {
		System.out.println("testBehaviorParameterization =======================================");
		List<String> result = useChoose(srcList, new Choose() {
			@Override
			public boolean test(String str) {

				return str != null && str.length() > 7;
			}

		});
		System.out.println(result);
	}

	// 行为参数化004：用 lambda 表达式来重构一下
	public void lambdaBehaviorParameterization() {
		System.out.println("lambdaBehaviorParameterization =======================================");
		List<String> result = useChoose(srcList, str -> str != null && str.length() > 7);
		System.out.println(result);
	}

	public static void main(String[] args) {
		LambdaDemo t = new LambdaDemo();
		t.testBehaviorParameterization();
		t.lambdaBehaviorParameterization();
	}
}
