package com.zhch.example.captcha.zhilian;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.io.Files;
import com.zhch.example.captcha.BackgroundUtil;
import com.zhch.example.captcha.SplitUtil;

public class LoginCaptcha {
	BackgroundUtil bgUtil = new BackgroundUtil(100, 6);
	SplitUtil splitUtil = new SplitUtil(2, 4);

	public void saveSplitImg(String file) {
		String savePath = "d:\\documents\\sou\\resume_import\\captcha\\zhilian_sep\\";
		String filename = Files.getNameWithoutExtension(file);
		BufferedImage image = bgUtil.removeBackgroud(file);
		try {
			BufferedImage[] parts = splitUtil.splitImage(image);
			for (int i = 0; i < 4; i++) {
				ImageIO.write(parts[i], "jpg", new File(savePath, filename + "_" + i + ".bmp"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testSingle() {

		//		String filepath= Classpath.class.getResource("/chaptcha/zhilian_check.bmp").getPath();
		String filepath = "d:\\documents\\sou\\resume_import\\captcha\\zhilian\\1449546076656.gif";
		BufferedImage image = bgUtil.removeBackgroud(filepath);
		try {
			ImageIO.write(image, "jpg", new File("d:\\temp\\d3\\captcha.jpg"));

			BufferedImage[] parts = splitUtil.splitImage(image);
			ImageIO.write(parts[0], "jpg", new File("d:\\temp\\d3\\captcha2.bmp"));
			ImageIO.write(parts[1], "jpg", new File("d:\\temp\\d3\\captcha3.bmp"));
			ImageIO.write(parts[2], "jpg", new File("d:\\temp\\d3\\captcha4.bmp"));
			ImageIO.write(parts[3], "jpg", new File("d:\\temp\\d3\\captcha5.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		File dir = new File("d:\\documents\\sou\\resume_import\\captcha\\zhilian\\");
		File[] files = dir.listFiles();
		int i = 0;
		for (File file : files) {
			saveSplitImg(file.getAbsolutePath());
			System.out.println("process gif : " + i++);
		}
	}

	public static void main(String[] args) {
		LoginCaptcha t = new LoginCaptcha();
		t.test();
		//		t.testSingle();
		System.out.println("over!!");
	}
}
