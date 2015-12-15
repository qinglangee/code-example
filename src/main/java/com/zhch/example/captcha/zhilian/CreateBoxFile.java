package com.zhch.example.captcha.zhilian;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

public class CreateBoxFile {
	public void createBoxFile() throws IOException {
		File srcDir = new File("d:\\temp\\d3\\tesseract\\gif");
		File destFile = new File("d:\\temp\\d3\\tesseract\\fake.box");
		List<File> files = Arrays.asList(srcDir.listFiles());
		Collections.sort(files);
		List<String> boxItems = new ArrayList<>();
		int index = 0;
		for (File file : files) {
			String filename = file.getName();
			String nameNoExt = Files.getNameWithoutExtension(file.getAbsolutePath());
			if (!filename.endsWith("gif") || nameNoExt.length() != 4) {
				continue;
			}

			List<String> init = new ArrayList<String>();
			init.add(" 8 10 21 27 ");
			init.add(" 22 7 38 26 ");
			init.add(" 39 8 54 26 ");
			init.add(" 55 7 68 26 ");
			for (int i = 0; i < nameNoExt.length(); i++) {
				char c = nameNoExt.charAt(i);
				StringBuilder sb = new StringBuilder().append(c);
				sb.append(init.get(i)).append(index);
				boxItems.add(sb.toString());
			}
			index++;
		}
		FileUtils.writeLines(destFile, boxItems);
	}

	public static void main(String[] args) throws IOException {
		CreateBoxFile t = new CreateBoxFile();
		t.createBoxFile();
		System.out.println("over!!");
	}
}
