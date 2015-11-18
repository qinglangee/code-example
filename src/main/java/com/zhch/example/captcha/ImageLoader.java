package com.zhch.example.captcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import com.zhch.example.java.Classpath;

public class ImageLoader {

	public int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;
	}

	public int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return 1;
		}
		return 0;
	}

	public BufferedImage removeBackgroud(String picFile) throws Exception {
		BufferedImage img = ImageIO.read(new File(picFile));
		int width = img.getWidth();
		int height = img.getHeight();
		int[][] diffs = new int[width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int diff = countDistance(img, x, y, 0.0);
				diffs[x][y] = diff;
				System.out.print(diff + " ");
				//				if (isWhite(img.getRGB(x, y)) == 1) {
			}
			System.out.println();
		}
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int diff = diffs[x][y];
				if (diff < 4) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	private int countDistance(BufferedImage img, int x, int y, double range) {
		int w = img.getWidth();
		int h = img.getHeight();
		int[][] points = new int[8][2];
		points[0][0] = x - 1;
		points[0][1] = y + 1;
		points[1][0] = x;
		points[1][1] = y + 1;
		points[2][0] = x + 1;
		points[2][1] = y + 1;
		points[3][0] = x - 1;
		points[3][1] = y;
		points[4][0] = x + 1;
		points[4][1] = y;
		points[5][0] = x - 1;
		points[5][1] = y - 1;
		points[6][0] = x;
		points[6][1] = y - 1;
		points[7][0] = x + 1;
		points[7][1] = y - 1;

		Color color = new Color(img.getRGB(x, y));

		int count = 0;
		for (int i = 0; i < 8; i++) {
			int a = points[i][0];
			int b = points[i][1];
			if (a > 0 && a < w && b > 0 && b < h) {
				Color nabor = new Color(img.getRGB(a, b));
				int rd = color.getRed() - nabor.getRed();
				int gd = color.getGreen() - nabor.getGreen();
				int bd = color.getBlue() - nabor.getBlue();
				Double sqrt = Math.sqrt(rd * rd + gd * gd + bd * bd);
				//				System.out.print(sqrt.intValue() + " ");
				if (sqrt > 10) {
					count++;
				}
			}
		}

		return count;
	}

	public void test() throws Exception {
		URL url = Classpath.class.getResource("/chaptcha/zhilian_check.bmp");
		BufferedImage image = removeBackgroud(url.getPath());
		ImageIO.write(image, "jpg", new File("d:\\temp\\d3\\captcha.jpg"));
	}

	public static void main(String[] args) throws Exception {
		ImageLoader t = new ImageLoader();
		t.test();
		System.out.println("over!!");
	}
}
