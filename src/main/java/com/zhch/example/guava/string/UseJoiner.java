package com.zhch.example.guava.string;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.google.common.base.Joiner;

public class UseJoiner {
	public void basicUse() {
		System.out.println("basicUse================================================");
		// list
		List<String> stringList = new ArrayList<String>();
		stringList.add("a");
		stringList.add("b");
		stringList.add("c");
		String link = Joiner.on("|").skipNulls().join(stringList);
		Assert.assertEquals(link, "a|b|c");
		System.out.println(link); // a|b|c

		// array
		String[] strArr = new String[] { "a", "b", "c", null };
		String arrStr = Joiner.on(",").skipNulls().join(strArr);
		Assert.assertEquals(arrStr, "a,b,c");
		System.out.println(arrStr); // a,b,c
		String arrNull= Joiner.on(",").useForNull("xxx").join(strArr);
		Assert.assertEquals(arrNull, "a,b,c,xxx");
	}

	public void useWithStringBuilder() throws IOException {
		System.out.println("useWithStringBuilder================================================");
		Joiner joiner = Joiner.on(",").skipNulls();
		StringBuilder stringBuilder = new StringBuilder("start:  ");
		// 添加到 StringBuilder
		joiner.appendTo(stringBuilder, "foo", "bar", "baz");
		System.out.println(stringBuilder); // start: foo,bar,baz

		// appendTo 可以用在 接口 Appendable
		FileWriter fileWriter = new FileWriter(new File("path"));
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(new Date());
		joiner.appendTo(fileWriter, dateList);
	}
	
	public void mapJoiner(){
		System.out.println("mapJoiner================================================");
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("abc", 12L);
		map.put("def", 13L);
		Joiner.MapJoiner mapJoiner = Joiner.on("#").withKeyValueSeparator("=");
		System.out.println(mapJoiner.join(map)); // 
	}

	public static void main(String[] args) throws IOException {
		UseJoiner t = new UseJoiner();
		t.basicUse();
		t.useWithStringBuilder();
		t.mapJoiner();
	}
}
