package com.zhch.example.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.sun.star.xml.sax.SAXException;

public class TikaParser {

	public void test() throws IOException, SAXException, TikaException {
		Tika tika = new Tika();
		//		InputStream stream = TikaParser.class.getResourceAsStream("test.doc");
		InputStream stream = new FileInputStream(new File("d:\\temp\\d3\\lvmengmeng.doc"));
		String result = tika.parseToString(stream);
		//		try(InputStream stream = TikaParser.class.getResourceAsStream("test.doc")) {
		//		}
		System.out.println(result);
	}

	public static void main(String[] args) throws SAXException, IOException, TikaException {
		TikaParser t = new TikaParser();
		t.test();
		System.out.println("over!!!");
	}

}
