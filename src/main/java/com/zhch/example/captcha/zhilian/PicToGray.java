package com.zhch.example.captcha.zhilian;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.io.Files;
import com.zhch.example.captcha.BackgroundUtil;
import com.zhch.example.captcha.SplitUtil;

/**
 * 图片转换为黑白
 * 
 * @author zhch
 *
 */
public class PicToGray {
	BackgroundUtil bgUtil = new BackgroundUtil(500, 10);
	SplitUtil splitUtil = new SplitUtil(2, 4);
	public void toWhiteBalck() {
		File srcDir = new File("d:\\temp\\d3\\tesseract\\data_out");
		File destDir = new File("d:\\temp\\d3\\tesseract\\data_gray");
		File[] files = srcDir.listFiles();
		for (File file : files) {
			String filename = file.getName();
			if (!filename.endsWith("gif")) {
				continue;
			}
			String nameNoExt = Files.getNameWithoutExtension(file.getAbsolutePath());
			BufferedImage image = bgUtil.removeBackgroud(file);

			try {
				ImageIO.write(image, "gif", new File(destDir, filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void splitAndMerge() {
		File srcDir = new File("d:\\temp\\d3\\tesseract\\data_out");
		File destDir = new File("d:\\temp\\d3\\tesseract\\data_split");
		File[] files = srcDir.listFiles();
		for (File file : files) {
			String filename = file.getName();
			if (!filename.endsWith("gif")) {
				continue;
			}

			BufferedImage image = bgUtil.removeBackgroud(file);
			try {
				BufferedImage[] parts = splitUtil.splitImage(image);
				int imgW = 25;
				int imgH = 10;
				int maxH = 0;
				for (int i = 0; i < parts.length; i++) {
					imgW += parts[i].getWidth();
					maxH = parts[i].getHeight() > maxH ? parts[i].getHeight() : maxH;
				}
				imgH += maxH;
				BufferedImage result = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
				for (int x = 0; x < imgW; x++) {
					for (int y = 0; y < imgH; y++) {
						result.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
				int start = 5;
				for (int i = 0; i < parts.length; i++) {
					BufferedImage img = parts[i];
					int w = img.getWidth();
					int h = img.getHeight();
					for (int x = 0; x < w; x++) {
						for (int y = 0; y < h; y++) {
							result.setRGB(i * 5 + x + start, 5 + y, img.getRGB(x, y));
						}
					}
					start += w;
				}
				ImageIO.write(result, "gif", new File(destDir, filename));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		PicToGray t = new PicToGray();
		t.splitAndMerge();
		//		t.toWhiteBalck();
		System.out.println("over!!");
	}
}
