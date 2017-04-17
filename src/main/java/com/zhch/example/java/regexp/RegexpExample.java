package com.zhch.example.java.regexp;

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

	public void test() {
		simple();
		replaceMultiLine();
		replaceWithGroup();
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

	public void replaceMultiLine() {
		System.out.println("replaceMultiLine========================");
		String multiLine = "abcd #→start→#seo#←end←#\r\n";
		multiLine += "defgh#→start→#seo#←end←#hahahaha\r\n";
		multiLine += "hijk";
		// 匹配替换多行， 默认就是匹配多行
		String result = multiLine.replaceAll("#→start→#|#←end←#", "");
		System.out.println("result:" + result);
	}

	/**
	 * 用捕获的内容替换
	 */
	public void replaceWithGroup() {
		System.out.println("replace With Group ============================================");
		String text = "sss1、aa 2、bb 13、cc 44、ddmmm";
		Matcher matcher = Pattern.compile("(\\d+、)").matcher(text);
		// 用第一个替换所有遇到的
		if (matcher.find()) {
			matcher.group();
			String allReplace = matcher.replaceAll("\n" + matcher.group(1));
			System.out.println(allReplace);
		}

		// 用每一个自己替换
		StringBuffer s = new StringBuffer();
		Matcher m = Pattern.compile("(\\d+、)").matcher(text);
		while (m.find()) {
			m.appendReplacement(s, "\n" + m.group(1));
		}
		m.appendTail(s);
		System.out.println(s.toString());
	}

	public static void main(String[] args) {
		RegexpExample t = new RegexpExample();
		t.test();
	}
}
