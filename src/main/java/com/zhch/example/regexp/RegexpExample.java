package com.zhch.example.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpExample {
	List<String> lines;
	
	public RegexpExample(){
		lines = new ArrayList<String>();
		lines.add("1. one two three");
		lines.add("2. operate is very simple");
		lines.add("3. word");
		lines.add("3. word more");
	}
	public void simple() {
		System.out.println("simple p1============================");
		Pattern p1 = Pattern.compile("t");
		for(String line : lines){
			Matcher m = p1.matcher(line);
			// 有匹配的就打印, 输出前两行
			if(m.find()){
				System.out.println(line);
			}
		}
		
		System.out.println("simple p2============================");
		Pattern p2 = Pattern.compile("3. word");
		for(String line : lines){
			Matcher m = p2.matcher(line);
			// 模式匹配整行就打印, 输出第三行
			if(m.matches()){
				System.out.println(line);
			}
		}
		
	}

	public static void main(String[] args) {
		RegexpExample t = new RegexpExample();
		t.simple();
	}
}
