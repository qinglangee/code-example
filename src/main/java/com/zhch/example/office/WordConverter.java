package com.zhch.example.office;

import java.io.File;
import java.net.URL;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * 
 * @author zhch
 * 
 *         Document https://code.google.com/p/jodconverter/wiki/GettingStarted
 *         wordpress -> java -> word_jodconverter.md
 * 
 *         Github repo https://github.com/mirkonasato/jodconverter<br>
 *         github clone
 *         https://github.com/liveSense/org.liveSense.framework.jodconverter
 * 
 *         example
 *         http://www.cnblogs.com/codeplus/archive/2011/10/22/2220952.html
 *         http://dangry.iteye.com/blog/858787
 *
 */
public class WordConverter {

	// 需要先安装 LibreOffice
	public static void main(String[] args) {
		URL url = WordConverter.class.getResource("/");
		File inputFile = new File(url.getPath(), "office/lagou_resume.doc");
		File outputFile = new File("d:\\temp\\d3\\mm.html");

		OfficeManager officeManager = new DefaultOfficeManagerConfiguration()
				.setOfficeHome("d:\\Program Files (x86)\\LibreOffice 5").buildOfficeManager();
		officeManager.start();

		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		converter.convert(inputFile, outputFile);

		officeManager.stop();

		System.out.println("over !!!");
	}
}