package com.zhch.example.captcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundUtil {
	Logger LOG = LoggerFactory.getLogger(BackgroundUtil.class);
	/** 周围颜色不同像素点的最小个数， 不同颜色的分界区分标准 */
	private int minDiffCountArround;
	/** 不同颜色系的最小距离，大于则认为是不同色系 */
	private double minDistance;

	private int bgColor = 0;
	private int bgCount = 0;

	public BackgroundUtil(int distance, int diffArround) {
		minDistance = distance;
		minDiffCountArround = diffArround;
	}

	boolean debug = false;

	void debug(String content, boolean newLine) {
		if (debug) {
			if (newLine) {
				System.out.println(content);
			} else {
				System.out.print(content);
			}
		}
	}

	/**
	 * 图片的文字与背景区分，转换成黑白色
	 * 
	 * @param picFile
	 * @return
	 */
	public BufferedImage removeBackgroud(String filePath) {
		return removeBackgroud(new File(filePath));
	}

	/**
	 * 图片的文字与背景区分，转换成黑白色
	 * 
	 * @param picFile
	 * @return
	 */
	public BufferedImage removeBackgroud(File picFile) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(picFile);
		} catch (IOException e) {
			LOG.error("读取验证码图片文件出错。", e);
			return null;
		}
		int w = img.getWidth();
		int h = img.getHeight();
		int[][] diffs = new int[w][h];
		Map<Integer, Integer> bgMap = new HashMap<>(); // 记录每个颜色的数量

		bgCount = 0;

		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int colorRgb = img.getRGB(x, y);
				// 判断是否是背景色（最多的颜色）
				Integer count = bgMap.get(colorRgb);
				if (count == null) {
					bgMap.put(colorRgb, 1);
				} else {
					bgMap.put(colorRgb, count + 1);
					if (count > bgCount) {
						bgCount = count;
						bgColor = colorRgb;
					}
				}

				int diff = countDistance(img, x, y, minDistance);
				diffs[x][y] = diff;
				debug(diff + " ", false);
				//				if (isWhite(img.getRGB(x, y)) == 1) {
			}
			debug("", true);
		}

		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int colorRgb = img.getRGB(x, y);
				int diff = diffs[x][y];
				//				if (diff > minDiffCountArround && colorRgb != bgColor) {
				if (diff < minDiffCountArround && colorRgb != bgColor) {
					result.setRGB(x, y, Color.BLACK.getRGB());
				} else {
					result.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		return result;
	}

	/**
	 * 计算 x,y 点 与周围8个点的颜色距离
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param range
	 * @return 大于 range 的点的数量
	 */
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
				if (sqrt > range) {
					count++;
				}
			}
		}

		return count;
	}


	public static void main(String[] args) throws Exception {
	}
}
