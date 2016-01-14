package com.zhch.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTools {
	Logger LOG = LoggerFactory.getLogger(FileTools.class);
	public static void writeFile(String filename, String content) {
		try {
			FileUtils.writeStringToFile(new File(filename), content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
