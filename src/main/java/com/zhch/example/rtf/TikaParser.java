package com.zhch.example.rtf;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.sun.star.xml.sax.SAXException;

public class TikaParser {

	public void testRtf() throws IOException, SAXException, TikaException {
		Tika tika = new Tika();
		//		InputStream stream = new FileInputStream(new File("d:\\temp\\d3\\lvmengmeng.doc"));
		InputStream stream = TikaParser.class.getResourceAsStream("rtf_demo_lvmengmeng.doc");
		String result = tika.parseToString(stream);
		//		try(InputStream stream = TikaParser.class.getResourceAsStream("test.doc")) {
		//		}
		System.out.println(result);
	}

	/**
	 * 二进制的doc文件解析
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void testDoc() throws IOException, SAXException, TikaException {
		Tika tika = new Tika();
		//		InputStream stream = new FileInputStream(new File("d:\\temp\\d3\\lvmengmeng.doc"));
		InputStream stream = TikaParser.class.getResourceAsStream("doc_demo_gonglibo.dot");
		String result = tika.parseToString(stream);
		//		try(InputStream stream = TikaParser.class.getResourceAsStream("test.doc")) {
		//		}
		System.out.println(result);
	}

	public static void main(String[] args) throws SAXException, IOException, TikaException {
		TikaParser t = new TikaParser();
		//		t.testRtf();
		t.testDoc();
		System.out.println("over!!!");
	}

}
