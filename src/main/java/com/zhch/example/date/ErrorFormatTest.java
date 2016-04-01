package com.zhch.example.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorFormatTest {
	/**
	 * 对不同错误格式的解析容忍 <br>
	 * [yyyy-MM-dd]--((:2016-01-01)) Fri Jan 01 00:00:00 CST 2016<br>
	 * [yyyy-MM-dd]--((2016/01/01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd]--((:2016-01-01 12:14:15)) Fri Jan 01 00:00:00 CST 2016<br>
	 * [yyyy-MM-dd HH:mm:ss]--((2016-01-01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd HH:mm:ss]--((2016/01/01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd HH:mm:ss]--((:2016-01-01 12:14:15)) Fri Jan 01 12:14:15 CST
	 * 2016<br>
	 * [HH:mm:ss]--((2016-01-01)) xxxxxxxxxxxx<br>
	 * [HH:mm:ss]--((2016/01/01)) xxxxxxxxxxxx<br>
	 * [HH:mm:ss]--((2016-01-01 12:14:15)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd'T'HH:mm:ss]--((2016-01-01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd'T'HH:mm:ss]--((2016/01/01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd'T'HH:mm:ss]--((2016-01-01 12:14:15)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd' 'HH:mm:ss]--((2016-01-01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd' 'HH:mm:ss]--((2016/01/01)) xxxxxxxxxxxx<br>
	 * [yyyy-MM-dd' 'HH:mm:ss]--((:2016-01-01 12:14:15)) Fri Jan 01 12:14:15 CST
	 * 2016<br>
	 * 
	 */
	public void test() {
		String[] formats = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss",
				"yyyy-MM-dd' 'HH:mm:ss" };
		String[] dateStrs = new String[] { "2016-5-8", "2016-3-12", "2016-01-01", "2016/01/01",
				"2016-01-01 12:14:15" };
		for (String format : formats) {
			for (String dateStr : dateStrs) {

				try {
					Date date = new SimpleDateFormat(format).parse(dateStr);
					System.out.println("[" + format + "]--((:" + dateStr + "))   " + date + "<br>");
				} catch (Exception e) {
					System.out.println("[" + format + "]--((" + dateStr + "))   xxxxxxxxxxxx<br>");
				}

			}
		}
	}

	public static void main(String[] args) {
		ErrorFormatTest t = new ErrorFormatTest();
		t.test();
	}
}
