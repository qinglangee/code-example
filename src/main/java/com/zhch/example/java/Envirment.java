package com.zhch.example.java;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Envirment {

	/** 打印环境变量 */
	public void printEnv() {
		Map map = System.getenv();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

	/** 看 java 系统是多少位的 */
	public void systemWidth() {
		System.out.println("model " + System.getProperty("sun.arch.data.model"));
	}

	public static void main(String[] args) {
		Envirment t = new Envirment();
		t.printEnv();
	}
}
