package com.zhch.example.guava.string;

import org.testng.Assert;

import com.google.common.base.Strings;

public class UseStrings {
	public void test() {
		// 补足字符串
		String foopad = Strings.padEnd("foo",6,'x');
		Assert.assertEquals(foopad, "fooxxx");
		String barpad = Strings.padEnd("bar1234567",6,'x');
		Assert.assertEquals(barpad, "bar1234567");
		// 前面补足
		String foopadstr = Strings.padStart("foo",6,'x');
		Assert.assertEquals(foopadstr, "xxxfoo");
		
	}

	public static void main(String[] args) {
		UseStrings t = new UseStrings();
		t.test();
		System.out.println("over!!");
	}
}
