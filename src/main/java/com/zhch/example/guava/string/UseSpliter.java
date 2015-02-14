package com.zhch.example.guava.string;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Splitter;

public class UseSpliter {
	public void basicUse() {
		System.out.println("basicUse===========================================================");
		Splitter s1 = Splitter.on("|");  // 普通分隔符
		print(s1.split("foo|bar|baz"));
		System.out.println("s1-----------------------------------");
		Splitter s2 = Splitter.on('|').trimResults();  // 子串会进行 trim 操作
		
		String empty = "||foo|   bar    |    baz| | |";
		print(s1.split(empty));
		System.out.println("s1-----------------------------------");
		print(s2.split(empty));
		System.out.println("s2-----------------------------------");
		
		String src = "abc123def456gh44";
		Splitter s3 = Splitter.onPattern("\\d");  // 使用正则表达式
		Splitter s4 = Splitter.onPattern("\\d+");
		List<String> list = s3.splitToList(src);
		print(list);
		System.out.println("s3-----------------------------------");
		print(s4.splitToList(src));
		System.out.println("s4-----------------------------------");
	}
	
	
	/**
	 * 打印结果
	 * @param items
	 */
	private void print(Iterable<String> items){
		Iterator<String> iterator = items.iterator();
		while(iterator.hasNext()){
			System.out.println("[" + iterator.next() + "]");
		}
	}

	public static void main(String[] args) {
		UseSpliter t = new UseSpliter();
		t.basicUse();
	}
}
