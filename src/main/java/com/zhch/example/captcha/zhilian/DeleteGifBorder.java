package com.zhch.example.captcha.zhilian;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zhch.example.captcha.SplitUtil;

public class DeleteGifBorder {
	SplitUtil splitUtil = new SplitUtil(2, 4);

	public void test() {

		File srcPath = new File("d:\\temp\\d3\\tesseract\\data\\");
		File savePath = new File("d:\\temp\\d3\\tesseract\\data_out\\");
		File[] files = srcPath.listFiles();
		for (File file : files) {
			if (!file.getName().endsWith("gif")) {
				continue;
			}
			try {
				BufferedImage img = ImageIO.read(file);
				BufferedImage noBorderImg = splitUtil.removeBorder(img);
				ImageIO.write(noBorderImg, "gif", new File(savePath, file.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		DeleteGifBorder t = new DeleteGifBorder();
		t.test();
		System.out.println("over!!");
	}
}
