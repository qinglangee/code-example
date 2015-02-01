package com.zhch.example.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpExample {
	List<String> lines;

	public RegexpExample() {
		lines = new ArrayList<String>();
		lines.add("1. yeye one two three");
		lines.add("2. yeye operate is very simple");
		lines.add("3. word");
		lines.add("C:\\fakepath\\image(46).jpeg");
		lines.add("C:/fakepath/image(46).jpeg");
		lines.add("image(46).jpeg");
	}

	public void simple() {
		System.out.println("simple p1============================");
		Pattern p1 = Pattern.compile("yeye");
		for (String line : lines) {
			Matcher m = p1.matcher(line);
			// 只要有匹配 的就打印, 输出前两行
			if (m.find()) {
				System.out.println(line);
			}
		}

		System.out.println("simple p2============================");
		Pattern p2 = Pattern.compile("3. word");
		for (String line : lines) {
			Matcher m = p2.matcher(line);
			// 必须整行 匹配就打印, 输出第三行
			if (m.matches()) {
				System.out.println(line);
			}
		}

		System.out.println("simple p3============================");
		Pattern  p3 = Pattern.compile("[\\\\|/]+([^\\\\|/]*?)$");
//		p3 = Pattern.compile("2..(.*)");
		for (String line : lines) {
			Matcher m = p3.matcher(line);
			// 输出被匹配的字符串, 匹配路径中的文件名
			if (m.find()) {
				System.out.println(m.group());
				
				String filename = m.group(1);
				System.out.println(filename);
				
			}
		}

	}

	public static void main(String[] args) {
		RegexpExample t = new RegexpExample();
		t.simple();
	}
}
