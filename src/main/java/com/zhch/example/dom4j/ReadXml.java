package com.zhch.example.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXml {
	public void test() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read("/home/lifeix/workspace_kepler/mediaconvertserver/pom.xml");
		String version = doc.getRootElement().attributeValue("version");

		Element serverEl = (Element)doc.getRootElement().selectSingleNode("server");
	}

	public static void main(String[] args) throws DocumentException {
		ReadXml t = new ReadXml();
		t.test();
	}
}
