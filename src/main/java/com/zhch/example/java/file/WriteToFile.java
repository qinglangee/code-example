package com.zhch.example.java.file;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteToFile {

	public void test() throws IOException {
		String sContent = "abc";
		String sFilePath = "d:\\temp\\d3\\abc.txt";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sFilePath), "UTF-8");
		out.write(sContent);
		out.flush();
		out.close();

		//使用FileWriter写入文件内容的代码为：

		FileWriter fw = new FileWriter(sFilePath);
		fw.write(sContent);
		fw.close();

		// 总结：

		//    FileWriter在写文件的时候在中文win下encoding基本是gb2312，在en的win下基本是iso-8859-1，总之不是utf-8。
		//        所以要创建一个utf-8的文件，用FileWriter是不行的。
	}

	public static void main(String[] args) throws IOException {
		WriteToFile t = new WriteToFile();
		t.test();
	}

}
